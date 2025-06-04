package com.api.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.demo.entity.Book;
import com.api.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired private BookRepository bookRepo;

    public Book addBook(Book book) {
        List<Book> existing = bookRepo.findByIsbn(book.getIsbn());
        if (!existing.isEmpty()) {
            Book ref = existing.get(0);
            if (!ref.getAuthor().equals(book.getAuthor()) || !ref.getTitle().equals(book.getTitle())) {
                throw new IllegalArgumentException("ISBN already exists with different title/author.");
            }
        }
        return bookRepo.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book borrowBook(Long bookId) {
        Book book = bookRepo.findByIdAndBorrowedFalse(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found or already borrowed"));
        book.setBorrowed(true);
        return bookRepo.save(book);
    }

    public Book returnBook(Long bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setBorrowed(false);
        return bookRepo.save(book);
    }
}
