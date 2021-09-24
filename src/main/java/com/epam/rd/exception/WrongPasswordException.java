package com.epam.rd.exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Oops! Password does not matched!!");
    }
}
