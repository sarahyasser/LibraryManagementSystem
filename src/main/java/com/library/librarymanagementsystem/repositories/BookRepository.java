package com.library.librarymanagementsystem.repositories;

import com.library.librarymanagementsystem.models.Book;
import com.library.librarymanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

}
