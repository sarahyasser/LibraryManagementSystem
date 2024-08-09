package com.library.librarymanagementsystem.controllers;

import com.library.librarymanagementsystem.dtos.BookRequestDTO;
import com.library.librarymanagementsystem.dtos.BookResponseDTO;
import com.library.librarymanagementsystem.models.Book;
import com.library.librarymanagementsystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookResponseDTO> response = books.stream()
                .map(bookService::toBookResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable("id") Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return new ResponseEntity<>(bookService.toBookResponseDTO(book.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
        Book savedBook = bookService.addBook(bookRequestDTO);
        return new ResponseEntity<>(bookService.toBookResponseDTO(savedBook), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable("id") Long id,
            @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        try {
            Book book = bookService.toBookEntity(bookRequestDTO);
            Book updatedBook = bookService.updateBook(id, book);
            return new ResponseEntity<>(bookService.toBookResponseDTO(updatedBook), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Book deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }
}
