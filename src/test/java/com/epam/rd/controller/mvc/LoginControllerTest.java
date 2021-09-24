package com.epam.rd.controller.mvc;

import com.epam.rd.dto.LoginDTO;
import com.epam.rd.exception.AccountDoesNotExistException;
import com.epam.rd.exception.WrongPasswordException;
import com.epam.rd.service.AccountServiceImpl;
import com.epam.rd.service.GroupServiceImpl;
import com.epam.rd.service.RecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
class LoginControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AccountServiceImpl accountServiceImpl;

    @MockBean
    GroupServiceImpl groupServiceImpl;

    @MockBean
    RecordServiceImpl recordServiceImpl;

    @Test
    public void testLoginUserThrowAccountDoesNotExistExceptionForExistedUser() throws Exception {
        when(accountServiceImpl.validateLogin(any())).thenThrow(new AccountDoesNotExistException());
        mvc.perform(post("/loginUser"))
                .andExpect(view().name("login"))
                .andExpect(model().attribute("error", "Oops! No Account Found!!"));
    }

    @Test
    public void testLoginUserThrowWrongPasswordExceptionForExistedUser() throws Exception {
        when(accountServiceImpl.validateLogin(any())).thenThrow(new WrongPasswordException());
        mvc.perform(post("/loginUser"))
                .andExpect(view().name("login"))
                .andExpect(model().attribute("error", "Oops! Password does not matched!!"));
    }

    @Test
    public void testLoginUserDoesNotThrowExceptionWhileValidationSucceed() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        when(accountServiceImpl.validateLogin(any())).thenReturn(any());
        mvc.perform(post("/loginUser").accept(MediaType.APPLICATION_JSON)
                        .flashAttr("loginDTO", loginDTO))
                .andExpect(view().name("redirect:/record"));
    }

    @Test
    public void testLoginUserReturnToLoginWhileValidationFailed() throws Exception {
        LoginDTO loginDTO = new LoginDTO("vishal kumar", "Vishal834019");
        mvc.perform(post("/loginUser").accept(MediaType.APPLICATION_JSON)
                        .flashAttr("loginDTO", loginDTO))
                .andExpect(view().name("login"));
    }
}