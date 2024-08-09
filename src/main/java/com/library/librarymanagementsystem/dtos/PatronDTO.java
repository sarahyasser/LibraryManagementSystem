package com.library.librarymanagementsystem.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PatronDTO {

    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 15, message = "Contact number must be less than or equal to 15 characters")
    private String contactNumber;

    private String contactInformation;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

}
