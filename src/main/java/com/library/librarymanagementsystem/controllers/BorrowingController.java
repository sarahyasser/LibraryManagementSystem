package com.library.librarymanagementsystem.controllers;

import com.library.librarymanagementsystem.dtos.BorrowingRequestDTO;
import com.library.librarymanagementsystem.services.BorrowingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_PATRON')")
    public ResponseEntity<String> borrowBook(@Valid @RequestBody BorrowingRequestDTO borrowingRequestDTO) {
        borrowingService.borrowBook(borrowingRequestDTO);
        return new ResponseEntity<>("Book borrowed successfully", HttpStatus.CREATED);
    }

    @PutMapping("/return/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_PATRON')")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId) {
        borrowingService.returnBook(bookId);
        return new ResponseEntity<>("Book returned successfully", HttpStatus.OK);
    }
}
