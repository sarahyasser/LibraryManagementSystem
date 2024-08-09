package com.library.librarymanagementsystem.repositories;

import com.library.librarymanagementsystem.models.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    Optional<Librarian> findByEmail(String name);
    Boolean existsByEmail(String email);
}
