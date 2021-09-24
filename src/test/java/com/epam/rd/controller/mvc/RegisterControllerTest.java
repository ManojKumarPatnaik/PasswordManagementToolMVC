package com.epam.rd.controller.mvc;

import com.epam.rd.dto.RegisterDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.exception.AccountAlreadyExistsException;
import com.epam.rd.exception.UnableToRegisterAccount;
import com.epam.rd.service.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AccountServiceImpl accountServiceImpl;

    @Test
    public void testRegisterUserDoesNotThrowExceptionWhileRegistrationSuccessful() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("Vishal Kumar", "KGR009517", "Vishal834019");
        Account account = new Account("Vishal Kumar", "KGR009517", "Vishal834019");
        when(accountServiceImpl.registerAccount(any())).thenReturn(account);
        mvc.perform(post("/registerUser").accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(view().name("login"))
                .andExpect(model().attribute("message", "User Registration Successfully!!"));
    }

    @Test
    public void testRegisterUserThrowExceptionWhenAccountAlreadyExists() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("Vishal Kumar", "KGR009517", "Vishal834019");
        when(accountServiceImpl.registerAccount(any())).thenThrow(new AccountAlreadyExistsException());
        mvc.perform(post("/registerUser").accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(view().name("register"))
                .andExpect(model().attribute("error", "Oops! Account already exists!!"));
    }
    @Test
    public void testRegisterUserRedirectToLogoutRegisterWhenValidationFailed() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("Vis32", "KGR009517", "Vishal834019");
        mvc.perform(post("/registerUser").accept(MediaType.APPLICATION_JSON)
                        .flashAttr("registerDTO", registerDTO))
                .andExpect(view().name("register"));
    }

    @Test
    public void testRegisterUserThrowExceptionWhenUnableToRegisterAccount() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("Vishal Kumar", "KGR009517", "Vishal834019");
        when(accountServiceImpl.registerAccount(any())).thenThrow(new UnableToRegisterAccount());
        mvc.perform(post("/registerUser")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(view().name("register"))
                .andExpect(model().attribute("error", "Unable to register!! Try again"));
    }
}