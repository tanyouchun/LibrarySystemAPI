package com.api.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.demo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsbn(String isbn);
    Optional<Book> findByIdAndBorrowedFalse(Long id);
}