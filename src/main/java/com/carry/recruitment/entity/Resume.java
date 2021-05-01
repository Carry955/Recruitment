package com.carry.recruitment.entity;


import java.util.ArrayList;

public class Resume {
    private int id;
    private String stu_id;
    private String cretime;
    private String avator;
    private String name;
    private String appraisal;
    private String birthday;
    private String gender;
    private String marry;
    private String Political;
    private String province;
    private String city;
    private String tel;
    private String email;
    private String web;
    private String address;
    private ArrayList<EduExp> edus;
    private ArrayList<WorkExp> works;
    private ArrayList<Skill> skills;

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCretime() {
        return cretime;
    }

    public void setCretime(String cretime) {
        this.cretime = cretime;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarry() {
        return marry;
    }

    public void setMarry(String marry) {
        this.marry = marry;
    }

    public String getPolitical() {
        return Political;
    }

    public void setPolitical(String political) {
        Political = political;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<EduExp> getEdus() {
        return edus;
    }

    public void setEdus(ArrayList<EduExp> edus) {
        this.edus = edus;
    }

    public ArrayList<WorkExp> getWorks() {
        return works;
    }

    public void setWorks(ArrayList<WorkExp> works) {
        this.works = works;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }
}
