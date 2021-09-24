package com.epam.rd.exception;

public class RecordAlreadyExistsException extends Exception {
    public RecordAlreadyExistsException() {
        super("Oops! Record already exists for url!!");
    }
}
