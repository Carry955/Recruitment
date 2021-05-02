package com.carry.recruitment.service;

import com.carry.recruitment.entity.Apply;
import com.carry.recruitment.entity.Company;
import com.carry.recruitment.entity.Hr;
import com.carry.recruitment.entity.Job;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface ComService {
    public boolean login(Hr hr, HttpServletRequest request);
    public Company getCompany(int company_id);
    public ArrayList<Job> getJobs(int company_id);
    public ArrayList<Apply> getAppies(int company_id, String status);
    public int credit(int company_id);
    public int editInfo(Company company);
    public int updateJob(Job job);
}
