package com.epam.rd.exception;

public class NoRecordFoundForAccountBasedOnUrl extends Exception {
    public NoRecordFoundForAccountBasedOnUrl() {
        super("Oops! No Record Found For Input Url!!");
    }
}
