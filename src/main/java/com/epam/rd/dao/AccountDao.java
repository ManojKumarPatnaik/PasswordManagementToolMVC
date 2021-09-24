package com.epam.rd.dao;

import com.epam.rd.dto.LoginDTO;
import com.epam.rd.dto.RegisterDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.exception.AccountAlreadyExistsException;
import com.epam.rd.exception.AccountDoesNotExistException;
import com.epam.rd.exception.UnableToRegisterAccount;
import com.epam.rd.exception.WrongPasswordException;

public interface AccountDao {
    Account registerAccount(RegisterDTO registerDTO) throws UnableToRegisterAccount, AccountAlreadyExistsException;

    String validateLogin(LoginDTO loginDTO) throws AccountDoesNotExistException, WrongPasswordException;
}
