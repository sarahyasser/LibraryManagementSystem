package com.library.librarymanagementsystem.services;

import com.library.librarymanagementsystem.dtos.BookRequestDTO;
import com.library.librarymanagementsystem.dtos.BookResponseDTO;
import com.library.librarymanagementsystem.models.Book;
import com.library.librarymanagementsystem.models.Librarian;
import com.library.librarymanagementsystem.models.Patron;
import com.library.librarymanagementsystem.models.User;
import com.library.librarymanagementsystem.repositories.BookRepository;
import com.library.librarymanagementsystem.repositories.LibrarianRepository;
import com.library.librarymanagementsystem.repositories.PatronRepository;
import com.library.librarymanagementsystem.repositories.UserRepository;
import com.library.librarymanagementsystem.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private LibrarianRepository librarianRepository;


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    private Librarian getLibrarian() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Librarian> optionalUser = librarianRepository.findByEmail(email);
        return optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    public Book addBook(BookRequestDTO bookRequestDTO) {
        Librarian librarian = getLibrarian();

        // Convert BookRequestDTO to Book entity
        Book book = modelMapper.map(bookRequestDTO, Book.class);
        book.setLibrarian(librarian);
        librarian.getBooks().add(book);

        // Save the book and return
        return bookRepository.save(book);
    }



    public Book updateBook(Long id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return bookRepository.save(book);
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    public void deleteBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Librarian librarian = book.getLibrarian();
            if (librarian != null) {
                librarian.getBooks().remove(book);
            }
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found");
        }

    }

    public BookRequestDTO toBookRequestDTO(Book book) {
        return modelMapper.map(book, BookRequestDTO.class);
    }

    public BookResponseDTO toBookResponseDTO(Book book) {
        return modelMapper.map(book, BookResponseDTO.class);
    }

    public Book toBookEntity(BookRequestDTO bookRequestDTO) {
        return modelMapper.map(bookRequestDTO, Book.class);
    }
}
