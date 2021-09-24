package com.epam.rd.entity;

import javax.persistence.*;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "url")
    private String url;
    @Column(name = "notes")
    private String notes;

    @ManyToOne
    private Group group;

    @ManyToOne
    private Account account;

    public Record() {

    }

    public Record(String userName, String password, String url, String notes) {
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.notes = notes;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "[" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", notes='" + notes + '\'' +
                ']';
    }
}
