package com.library.librarymanagementsystem.unitTests;

import com.library.librarymanagementsystem.controllers.BookController;
import com.library.librarymanagementsystem.dtos.BookRequestDTO;
import com.library.librarymanagementsystem.dtos.BookResponseDTO;
import com.library.librarymanagementsystem.models.Book;
import com.library.librarymanagementsystem.models.Librarian;
import com.library.librarymanagementsystem.models.Role;
import com.library.librarymanagementsystem.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        when(bookService.getAllBooks()).thenReturn(books);
        when(bookService.toBookResponseDTO(any(Book.class))).thenReturn(new BookResponseDTO());

        ResponseEntity<List<BookResponseDTO>> response = bookController.getAllBooks();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));
        when(bookService.toBookResponseDTO(book)).thenReturn(new BookResponseDTO());

        ResponseEntity<BookResponseDTO> response = bookController.getBookById(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetBookById_NotFound() {
        Long bookId = 1L;
        when(bookService.getBookById(bookId)).thenReturn(Optional.empty());

        ResponseEntity<BookResponseDTO> response = bookController.getBookById(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void testAddBook() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO();

        // Creating a Librarian object with necessary fields
        Librarian librarian = new Librarian("John Doe", "123 Street", "1234567890", "johndoe@example.com", "password123", Role.ROLE_LIBRARIAN);

        // Creating a Book object and associating it with the librarian
        Book book = new Book("Test Title", "Test Author", 2024, "1234567890", 5, librarian);

        when(bookService.addBook(bookRequestDTO)).thenReturn(book);
        when(bookService.toBookResponseDTO(book)).thenReturn(new BookResponseDTO());

        ResponseEntity<BookResponseDTO> response = bookController.addBook(bookRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void testUpdateBook() {
        Long bookId = 1L;
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        Book book = new Book();
        when(bookService.toBookEntity(bookRequestDTO)).thenReturn(book);
        when(bookService.updateBook(bookId, book)).thenReturn(book);
        when(bookService.toBookResponseDTO(book)).thenReturn(new BookResponseDTO());

        ResponseEntity<BookResponseDTO> response = bookController.updateBook(bookId, bookRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void testUpdateBook_NotFound() {
        Long bookId = 1L;
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        Book book = new Book();
        when(bookService.toBookEntity(bookRequestDTO)).thenReturn(book);
        when(bookService.updateBook(bookId, book)).thenThrow(new RuntimeException("Book not found"));

        ResponseEntity<BookResponseDTO> response = bookController.updateBook(bookId, bookRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void testDeleteBook() {
        Long bookId = 1L;
        doNothing().when(bookService).deleteBook(bookId);

        ResponseEntity<String> response = bookController.deleteBook(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Book deleted successfully", response.getBody());
    }

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void testDeleteBook_NotFound() {
        Long bookId = 1L;
        doThrow(new RuntimeException("Book not found")).when(bookService).deleteBook(bookId);

        ResponseEntity<String> response = bookController.deleteBook(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", response.getBody());
    }
}