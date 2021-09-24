package com.epam.rd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class Account {
    @Column(name = "account_name")
    private String accountName;

    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    public Account() {

    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Account(String accountName, String userName, String password) {
        this.accountName = accountName;
        this.userName = userName;
        this.password = password;
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

    @Override
    public String toString() {
        return "[" +
                " accountName='" + accountName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ']';
    }

    @OneToMany(mappedBy = "account")
    private Collection<Group> group;

    public Collection<Group> getGroup() {
        return group;
    }

    public void setGroup(Collection<Group> group) {
        this.group = group;
    }
}
