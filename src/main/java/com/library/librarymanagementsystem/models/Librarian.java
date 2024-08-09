package com.library.librarymanagementsystem.models;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Librarian extends User {
    @OneToMany(mappedBy = "librarian")
    private Set<Book> books;

    public Librarian() {
        super();
    }

    // Constructor with inherited fields
    public Librarian(String name, String contactInformation, String contactNumber, String email, String password, Role role) {
        super(name, contactInformation, contactNumber, email, password, role);
    }
}
