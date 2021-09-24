package com.epam.rd.exception;

public class RecordDoesNotExistException extends Exception {
    public RecordDoesNotExistException() {
        super("Oops! No Record Found!!!");
    }
}
