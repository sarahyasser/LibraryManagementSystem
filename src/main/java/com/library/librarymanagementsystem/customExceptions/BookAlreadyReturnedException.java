package com.library.librarymanagementsystem.customExceptions;

public class BookAlreadyReturnedException extends RuntimeException {
    public BookAlreadyReturnedException(String message) {
        super(message);
    }
}