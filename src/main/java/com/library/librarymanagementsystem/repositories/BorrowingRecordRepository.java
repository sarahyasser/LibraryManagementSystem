package com.library.librarymanagementsystem.repositories;

import com.library.librarymanagementsystem.models.Book;
import com.library.librarymanagementsystem.models.BorrowingRecord;
import com.library.librarymanagementsystem.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBookAndPatronAndReturnDateIsNull(Book book, Patron patron);
    Optional<BorrowingRecord> findById(Long id);
}
