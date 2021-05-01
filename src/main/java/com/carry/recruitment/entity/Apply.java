package com.carry.recruitment.entity;

import java.util.ArrayList;

public class Apply {
    private int id;
    private String stu_id;
    private String posttime;
    private String state;
    private Job job;
    private Resume resume;

    public Apply() {
    }

    public Apply(int id) {
        this.id = id;
    }

    public Apply(String stu_id, String posttime, String state, Job job, Resume resume) {
        this.stu_id = stu_id;
        this.posttime = posttime;
        this.state = state;
        this.job = job;
        this.resume = resume;
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

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
}
