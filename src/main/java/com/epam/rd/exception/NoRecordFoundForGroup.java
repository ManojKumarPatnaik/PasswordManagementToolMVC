package com.epam.rd.exception;

public class NoRecordFoundForGroup extends Exception{
    public NoRecordFoundForGroup(){
        super("Oops! No Record found for group");
    }
}
