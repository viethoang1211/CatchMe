package com.example.catchme;


import java.time.LocalDate;

public class User {
    private String id;
    private String name;
    private String mail;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    private LocalDate birthday;

    public User(String id, String name, String mail, String phone, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.birthday = birthday;
    }
    // Location, Avatar not done



}
