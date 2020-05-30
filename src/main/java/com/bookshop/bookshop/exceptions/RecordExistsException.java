package com.bookshop.bookshop.exceptions;

public class RecordExistsException extends RuntimeException {
    private static final long serialVersionUID = 1348771109171435607L;
    public RecordExistsException(String message)
    {
        super(message);
    }
}
