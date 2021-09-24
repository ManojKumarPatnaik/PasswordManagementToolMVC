package com.epam.rd.controller.mvc;

import com.epam.rd.dto.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("login", "loginDTO", new LoginDTO()).addObject("message", "Logged out!!");
    }
}
