package com.carry.recruitment.service;

import com.carry.recruitment.entity.Job;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface JobService {
    public List<Job> getAllJobs();
    public Job getJob(int job_id);
    public Map<String, Object> search(String keyword, String city, String category, int page, int rows);
    public int addJob(Job job);
    public int delJob(int job_id);
    public boolean isApplied(String id ,int job_id);
    public boolean isFavorited(String id, int job_id);
}
