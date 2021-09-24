package com.epam.rd.dao;

import com.epam.rd.converter.Convert;
import com.epam.rd.dto.LoginDTO;
import com.epam.rd.dto.RegisterDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.exception.AccountAlreadyExistsException;
import com.epam.rd.exception.AccountDoesNotExistException;
import com.epam.rd.exception.WrongPasswordException;
import com.epam.rd.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Component
public class AccountDaoImpl implements AccountDao {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account registerAccount(RegisterDTO registerDTO) throws AccountAlreadyExistsException {
        Account accountConverted = Convert.convertToEntity(registerDTO);
        boolean existAccount = accountRepository.existsByUserName(accountConverted.getUserName());
        if (existAccount) {
            throw new AccountAlreadyExistsException();
        }
        accountConverted.setPassword(sha256Hex(accountConverted.getPassword()));
        return accountRepository.save(accountConverted);
    }

    @Override
    public String validateLogin(LoginDTO loginDTO) throws AccountDoesNotExistException, WrongPasswordException {
        Account account = Convert.convertToEntity(loginDTO);
        account.setPassword(sha256Hex(account.getPassword()));
        Account accountByUserName = accountRepository.findByUserName(account.getUserName());
        if (accountByUserName == null) {
            throw new AccountDoesNotExistException();
        } else {
            if (accountByUserName.getPassword().equals(account.getPassword())) {
                return accountByUserName.getAccountName();
            } else {
                throw new WrongPasswordException();
            }
        }
    }
}
