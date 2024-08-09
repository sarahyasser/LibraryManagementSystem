//package com.library.librarymanagementsystem.tests;
//
//import com.library.librarymanagementsystem.controllers.BookController;
//import com.library.librarymanagementsystem.dtos.BookRequestDTO;
//import com.library.librarymanagementsystem.dtos.BookResponseDTO;
//import com.library.librarymanagementsystem.models.Book;
//import com.library.librarymanagementsystem.models.Librarian;
//import com.library.librarymanagementsystem.models.Role;
//import com.library.librarymanagementsystem.security.CustomUserDetailsService;
//import com.library.librarymanagementsystem.security.JwtService;
//import com.library.librarymanagementsystem.services.BookService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//public class BookControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private BookService bookService;
//    @MockBean
//    private JwtService jwtService;
//    @MockBean
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Test
//    public void testLibrarianConstructorAndBookRelation() {
//        // Arrange
//        Librarian librarian = new Librarian(
//                "John Doe",
//                "123 Library St.",
//                "555-1234",
//                "john.doe@example.com",
//                "securepassword",
//                Role.LIBRARIAN
//        );
//
//        Book book1 = new Book("Effective Java", "Joshua Bloch", 2008, "978-0134685991", 10, librarian);
//        Book book2 = new Book("Clean Code", "Robert C. Martin", 2008, "978-0132350884", 5, librarian);
//
//        HashSet<Book> books = new HashSet<>();
//        books.add(book1);
//        books.add(book2);
//
//        librarian.setBooks(books);
//
//        // Act & Assert
//        assertNotNull(librarian);
//        assertEquals("John Doe", librarian.getName());
//        assertEquals(2, librarian.getBooks().size());
//        assertEquals("Effective Java", librarian.getBooks().iterator().next().getTitle());
//    }
//
//}
