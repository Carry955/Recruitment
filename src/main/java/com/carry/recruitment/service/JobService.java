package com.carry.recruitment.service;

import com.carry.recruitment.entity.Job;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface JobService {
    public List<Job> getAllJobs();
    public Job getJob(int job_id);
    public ArrayList<Job> search(String keyword, String city, String category);
    public int addJob(Job job);
    public int delJob(int job_id);
}
