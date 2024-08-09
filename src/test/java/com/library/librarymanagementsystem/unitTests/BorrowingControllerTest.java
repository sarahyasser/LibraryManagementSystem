package com.library.librarymanagementsystem.unitTests;

import com.library.librarymanagementsystem.controllers.BorrowingController;
import com.library.librarymanagementsystem.customExceptions.BookNotAvailableException;
import com.library.librarymanagementsystem.dtos.BorrowingRequestDTO;
import com.library.librarymanagementsystem.models.Book;
import com.library.librarymanagementsystem.models.Librarian;
import com.library.librarymanagementsystem.models.Role;
import com.library.librarymanagementsystem.services.BorrowingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BorrowingControllerTest {

    @Mock
    private BorrowingService borrowingService;

    @InjectMocks
    private BorrowingController borrowingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(authorities = "ROLE_PATRON")
    void testBorrowBook() {
        BorrowingRequestDTO borrowingRequestDTO = new BorrowingRequestDTO();
        borrowingRequestDTO.setBookId(1L);

        ResponseEntity<String> response = borrowingController.borrowBook(borrowingRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Book borrowed successfully", response.getBody());
        verify(borrowingService, times(1)).borrowBook(borrowingRequestDTO);
    }
    @Test
    @WithMockUser(authorities = "ROLE_PATRON")
    void testReturnBook() {
        Long bookId = 1L;

        ResponseEntity<String> response = borrowingController.returnBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book returned successfully", response.getBody());
        verify(borrowingService, times(1)).returnBook(bookId);
    }
}
