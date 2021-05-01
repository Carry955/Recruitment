package com.carry.recruitment.entity;


import java.util.List;

public class Job {
    private int id;
    private String name;
    private String settime;
    private String salary;
    private String city;
    private String category;
    private String num;
    private String edu;
    private Company company;
    private List<String> desc;
    private List<String> require;

    public Job() {
    }

    public Job(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSettime() {
        return settime;
    }

    public void setSettime(String settime) {
        this.settime = settime;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        if(!salary.isEmpty()){
            this.salary = salary;
        }else{
            this.salary = "面议";
        }

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    public List<String> getRequire() {
        return require;
    }

    public void setRequire(List<String> require) {
        this.require = require;
    }
}
