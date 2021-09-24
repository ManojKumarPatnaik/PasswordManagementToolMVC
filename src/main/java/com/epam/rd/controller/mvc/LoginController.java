package com.epam.rd.controller.mvc;

import com.epam.rd.dto.LoginDTO;
import com.epam.rd.dto.RecordDTO;
import com.epam.rd.exception.AccountDoesNotExistException;
import com.epam.rd.exception.WrongPasswordException;
import com.epam.rd.service.AccountServiceImpl;
import com.epam.rd.service.GroupServiceImpl;
import com.epam.rd.service.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    AccountServiceImpl accountServiceImpl;
    @Autowired
    GroupServiceImpl groupServiceImpl;
    @Autowired
    RecordServiceImpl recordServiceImpl;

    @PostMapping(value = "/loginUser")
    public ModelAndView loginUser(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("login");
        }
        try {
            String accountName = accountServiceImpl.validateLogin(loginDTO);
            setValues(loginDTO, session, accountName);
            return new ModelAndView("redirect:/record", "recordDTO", new RecordDTO());
        } catch (AccountDoesNotExistException | WrongPasswordException e) {
            return handleException(e);
        }
    }

    private void setValues(LoginDTO loginDTO, HttpSession session, String accountName) {
        recordServiceImpl.setAccount(loginDTO);
        groupServiceImpl.setAccount(loginDTO);
        session.setAttribute("accountName", accountName);
        session.setAttribute("account", loginDTO);
    }

    public ModelAndView handleException(Exception ex) {
        return new ModelAndView("login", "loginDTO", new LoginDTO()).addObject("error", ex.getMessage());
    }
}
