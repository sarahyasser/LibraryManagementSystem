package com.library.librarymanagementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String contactInformation;
    @NotBlank(message = "Contact number is required")
    private String contactNumber;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;

    public User(String email, String password, Role role) {
        this.email =email;
        this.password = password;
        this.role = role;
    }
    public User(String name, String contactInformation, String contactNumber, String email, String password, Role role) {
        this.name = name;
        this.contactInformation = contactInformation;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
