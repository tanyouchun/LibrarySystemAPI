package com.api.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.demo.entity.Borrower;
import com.api.demo.repository.BorrowerRepository;

@Service
public class BorrowerService {

    @Autowired private BorrowerRepository borrowerRepo;

    public Borrower registerBorrower(Borrower borrower) {
        return borrowerRepo.save(borrower);
    }
}
