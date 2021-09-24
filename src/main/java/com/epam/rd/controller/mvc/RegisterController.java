package com.epam.rd.controller.mvc;

import com.epam.rd.dto.LoginDTO;
import com.epam.rd.dto.RegisterDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.exception.AccountAlreadyExistsException;
import com.epam.rd.exception.UnableToRegisterAccount;
import com.epam.rd.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @PostMapping(value = "/registerUser")
    public ModelAndView registerUser(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }
        try {
            Account registeredAccount = accountServiceImpl.registerAccount(registerDTO);
            return new ModelAndView("login", "loginDTO", new LoginDTO())
                    .addObject("message", "User Registration Successfully!!");
        } catch (AccountAlreadyExistsException | UnableToRegisterAccount e) {
            return handleException(e);
        }
    }

    public ModelAndView handleException(Exception ex) {
        return new ModelAndView("register", "registerDTO", new RegisterDTO())
                .addObject("error", ex.getMessage());
    }
}
