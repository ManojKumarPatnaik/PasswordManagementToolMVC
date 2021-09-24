package com.epam.rd.controller.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LogoutController.class)
class LogoutControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void testHome() throws Exception {
        mvc.perform(get("/logout")).andExpect(view().name("login")).andExpect(model().attribute("message", "Logged out!!"));
    }
}