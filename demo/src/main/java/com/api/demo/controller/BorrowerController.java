package com.api.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.demo.entity.Borrower;
import com.api.demo.service.BorrowerService;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    @Autowired private BorrowerService borrowerService;

    @PostMapping
    public ResponseEntity<Borrower> registerBorrower(@RequestBody Borrower borrower) {
        return ResponseEntity.ok(borrowerService.registerBorrower(borrower));
    }
}
