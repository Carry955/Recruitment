package com.carry.recruitment.entity;

public class Favorite {
    private int id;
    private String stu_id;
    private Job job;
    private String settime;

    public Favorite() {
    }

    public Favorite(int id, String stu_id, Job job, String settime) {
        this.id = id;
        this.stu_id = stu_id;
        this.job = job;
        this.settime = settime;
    }

    public Favorite(int id, String stu_id) {
        this.id = id;
        this.stu_id = stu_id;
    }

    public Favorite(String stu_id, Job job, String settime) {
        this.stu_id = stu_id;
        this.job = job;
        this.settime = settime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getSettime() {
        return settime;
    }

    public void setSettime(String settime) {
        this.settime = settime;
    }
}
