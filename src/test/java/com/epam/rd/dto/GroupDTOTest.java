package com.epam.rd.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupDTOTest {
    GroupDTO groupDTO;

    @BeforeEach
    void setup() {
        groupDTO = new GroupDTO();
    }

    @Test
    void testName() {
        groupDTO.setName("Google");
        assertEquals("Google", groupDTO.getName());
    }

    @Test
    void testDescription() {
        groupDTO.setDescription("adding google records");
        assertEquals("adding google records", groupDTO.getDescription());
    }
}