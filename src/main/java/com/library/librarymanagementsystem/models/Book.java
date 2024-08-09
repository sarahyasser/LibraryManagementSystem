package com.library.librarymanagementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Author is required")
    private String author;

    private int publicationYear;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "ISBN is required")
    private String isbn;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @ManyToOne
    private Librarian librarian;

    @OneToMany(mappedBy = "book")
    private Set<BorrowingRecord> borrowingRecords;

    public Book( String title, String author, int publicationYear, String isbn, int quantity, Librarian librarian) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.quantity = quantity;
        this.librarian = librarian;
    }

}
