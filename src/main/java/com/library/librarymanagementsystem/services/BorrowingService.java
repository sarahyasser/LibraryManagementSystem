package com.library.librarymanagementsystem.services;

import com.library.librarymanagementsystem.customExceptions.*;
import com.library.librarymanagementsystem.dtos.BorrowingRequestDTO;
import com.library.librarymanagementsystem.models.Book;
import com.library.librarymanagementsystem.models.BorrowingRecord;
import com.library.librarymanagementsystem.models.Patron;
import com.library.librarymanagementsystem.repositories.BookRepository;
import com.library.librarymanagementsystem.repositories.BorrowingRecordRepository;
import com.library.librarymanagementsystem.repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    private Patron getPatron() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Patron> optionalPatron = patronRepository.findByEmail(email);
        return optionalPatron.orElseThrow(() -> new UnauthorizedException("User not found"));
    }

    @Transactional
    public void borrowBook(BorrowingRequestDTO borrowingRequestDTO) {
        Book book = bookRepository.findById(borrowingRequestDTO.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        Patron patron = getPatron();

        if (book.getQuantity() <= 0) {
            throw new BookNotAvailableException("Book is not available");
        }

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowingDate(borrowingRequestDTO.getBorrowingDate() != null ? borrowingRequestDTO.getBorrowingDate() : LocalDate.now());
        borrowingRecordRepository.save(record);

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
    }


    @Transactional
    public void returnBook(Long recordId) {
        // Fetch the BorrowingRecord by its ID
        BorrowingRecord record = borrowingRecordRepository.findById(recordId)
                .orElseThrow(() -> new BorrowingRecordNotFoundException("Borrowing record not found"));

        // Extract the Book from the BorrowingRecord
        Book book = record.getBook();

        // Check if the record is already returned
        if (record.getReturnDate() != null) {
            throw new BookAlreadyReturnedException("Book has already been returned");
        }

        // Update the BorrowingRecord with the return date
        record.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(record);

        // Increment the book's quantity
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
    }

}
