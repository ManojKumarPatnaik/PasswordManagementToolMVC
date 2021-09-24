package com.epam.rd.service;

import com.epam.rd.dao.AccountDaoImpl;
import com.epam.rd.dto.LoginDTO;
import com.epam.rd.dto.RegisterDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.exception.AccountAlreadyExistsException;
import com.epam.rd.exception.AccountDoesNotExistException;
import com.epam.rd.exception.UnableToRegisterAccount;
import com.epam.rd.exception.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDaoImpl accountDaoImpl;

    @Override
    public Account registerAccount(RegisterDTO registerDTO) throws UnableToRegisterAccount, AccountAlreadyExistsException {
        return accountDaoImpl.registerAccount(registerDTO);
    }

    @Override
    public String validateLogin(LoginDTO loginDTO) throws AccountDoesNotExistException, WrongPasswordException {
        return accountDaoImpl.validateLogin(loginDTO);
    }
}
