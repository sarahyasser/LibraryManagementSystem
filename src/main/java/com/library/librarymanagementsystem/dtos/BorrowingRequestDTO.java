package com.library.librarymanagementsystem.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Setter
@Getter
public class BorrowingRequestDTO {
    @NotNull(message = "Book ID is required")
    private Long bookId;
    private LocalDate borrowingDate;
}