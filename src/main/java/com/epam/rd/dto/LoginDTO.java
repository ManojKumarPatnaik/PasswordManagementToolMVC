package com.epam.rd.dto;

import javax.persistence.Id;
import javax.validation.constraints.Pattern;

public class LoginDTO {
    @Id
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[0-9])(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper,numeric}!")
    private String userName;

    @Pattern(regexp = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper,lower,numeric}!")
    private String password;

    public LoginDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginDTO() {
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
