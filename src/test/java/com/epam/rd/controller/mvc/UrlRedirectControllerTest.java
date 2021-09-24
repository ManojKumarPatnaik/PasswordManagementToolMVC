package com.epam.rd.controller.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UrlRedirectController.class)
class UrlRedirectControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void testHome() throws Exception {
        mvc.perform(get("/")).andExpect(view().name("login"));
    }
    @Test
    public void testLogin() throws Exception {
        mvc.perform(get("/login")).andExpect(view().name("login"));
    }
    @Test
    public void testRegister() throws Exception {
        mvc.perform(get("/register")).andExpect(view().name("register"));
    }
}