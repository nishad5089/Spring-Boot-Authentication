package com.bookshop.bookshop.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1348771109171435607L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
