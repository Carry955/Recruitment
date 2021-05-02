package com.carry.recruitment.entity;

public class Teacher {
    private int id;
    private String username;
    private String password;
    private String img;

    public void setImg(String img) {
        this.img = img;
    }

    public Teacher() {
    }

    public Teacher(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getImg() {
        return img;
    }
}
