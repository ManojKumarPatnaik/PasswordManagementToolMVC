package com.epam.rd.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecordDTOTest {
    RecordDTO recordDTO;

    @BeforeEach
    void setup() {
        recordDTO = new RecordDTO();
    }

    @Test
    void testUserName() {
        recordDTO.setUserName("KGR009517");
        assertEquals("KGR009517", recordDTO.getUserName());
    }

    @Test
    void testPassword() {
        recordDTO.setPassword("Vishal834019");
        assertEquals("Vishal834019", recordDTO.getPassword());
    }

    @Test
    void testUrl() {
        recordDTO.setUrl("http://www.mastercard.com");
        assertEquals("http://www.mastercard.com", recordDTO.getUrl());
    }

    @Test
    void testNotes() {
        recordDTO.setNotes("adding record");
        assertEquals("adding record", recordDTO.getNotes());
    }
}