package com.library.librarymanagementsystem.repositories;

import com.library.librarymanagementsystem.models.Patron;
import com.library.librarymanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    Optional<Patron> findByEmail(String name);
    Boolean existsByEmail(String email);
}
