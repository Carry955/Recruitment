package com.carry.recruitment.entity;


import com.alibaba.excel.annotation.ExcelProperty;

public class Stu {
    @ExcelProperty(index = 0)
    private String id;

    @ExcelProperty(index = 1)
    private String name;
    private String password;

    @ExcelProperty(index = 2)
    private String gender;

    @ExcelProperty(index = 3)
    private String address;
    @ExcelProperty(index = 4)
    private String email;
    @ExcelProperty(index = 5)
    private String tel;
    private String img;
    @ExcelProperty(index = 6)
    private String college;
    @ExcelProperty(index = 7)
    private String major;

    public Stu() {
    }

    public Stu(String id, String password) {
        this.id = id;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", img='" + img + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                '}';
    }
}
