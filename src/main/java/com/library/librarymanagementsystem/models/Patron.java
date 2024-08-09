package com.library.librarymanagementsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
public class Patron extends User {
    @OneToMany(mappedBy = "patron")
    private Set<BorrowingRecord> borrowingRecords;

    // Getters and Setters
}