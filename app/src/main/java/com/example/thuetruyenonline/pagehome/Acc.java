package com.example.thuetruyenonline.pagehome;

public class Acc {
    String email;
    String pass;
    public  Acc(){}

    public Acc(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
