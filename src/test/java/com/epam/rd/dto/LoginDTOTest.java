package com.epam.rd.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginDTOTest {
    LoginDTO loginDTO;

    @BeforeEach
    void setup() {
        loginDTO = new LoginDTO();
    }

    @Test
    void testUserName() {
        loginDTO.setUserName("KGR009517");
        assertEquals("KGR009517", loginDTO.getUserName());
    }

    @Test
    void testPassword() {
        loginDTO.setPassword("Vishal834019");
        assertEquals("Vishal834019", loginDTO.getPassword());
    }
}