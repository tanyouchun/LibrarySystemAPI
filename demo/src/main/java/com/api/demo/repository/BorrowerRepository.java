package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.demo.entity.Borrower;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {}