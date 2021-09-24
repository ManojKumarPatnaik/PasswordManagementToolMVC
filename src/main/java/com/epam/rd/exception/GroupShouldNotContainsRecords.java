package com.epam.rd.exception;

public class GroupShouldNotContainsRecords extends Exception {
    public GroupShouldNotContainsRecords(){
        super("Oops! Group should be empty before deletion!!");
    }
}
