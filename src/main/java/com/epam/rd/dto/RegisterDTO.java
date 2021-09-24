package com.epam.rd.dto;

import javax.persistence.Id;
import javax.validation.constraints.Pattern;

public class RegisterDTO {
    @Pattern(regexp = "^[a-zA-Z_ ]{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper,lower,whitespace,numeric}!")
    private String accountName;

    @Pattern(regexp = "(?=.*[A-Z])(?=.*[0-9])(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper,numeric}!")
    @Id
    private String userName;

    @Pattern(regexp = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper,lower,numeric}!")
    private String password;

    public RegisterDTO(String accountName, String userName, String password) {
        this.accountName = accountName;
        this.userName = userName;
        this.password = password;
    }

    public RegisterDTO() {

    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
