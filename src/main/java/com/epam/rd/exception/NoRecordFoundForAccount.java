package com.epam.rd.exception;

public class NoRecordFoundForAccount extends Exception {
    public NoRecordFoundForAccount(){
        super("Oops! No Record Found!!");
    }
}
