package com.api.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.demo.entity.Book;
import com.api.demo.entity.Borrower;
import com.api.demo.service.BookService;
import com.api.demo.repository.BookRepository;
import com.api.demo.repository.BorrowerRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired private BookService bookService;
    @Autowired private BookRepository bookRepository;         
    @Autowired private BorrowerRepository borrowerRepository; 
    
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

  // Borrow a book
    @PostMapping("/borrow/{borrowerId}/book/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        Optional<Borrower> borrowerOpt = borrowerRepository.findById(borrowerId);

        if (bookOpt.isEmpty() || borrowerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book or borrower not found");
        }

        Book book = bookOpt.get();
        if (book.isBorrowed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is already borrowed");
        }

        book.setBorrowed(true);
        book.setBorrower(borrowerOpt.get());
        bookRepository.save(book);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    // Return a book
    @PostMapping("/return/{borrowerId}/book/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        Book book = bookOpt.get();
        if (!book.isBorrowed() || book.getBorrower() == null || !book.getBorrower().getId().equals(borrowerId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is not borrowed by this borrower");
        }

        book.setBorrowed(false);
        book.setBorrower(null);
        bookRepository.save(book);
        return ResponseEntity.ok("Book returned successfully");
    }

}
