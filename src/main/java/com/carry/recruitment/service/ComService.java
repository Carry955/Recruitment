package com.carry.recruitment.service;

import com.carry.recruitment.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface ComService {
    public boolean login(Hr hr, HttpServletRequest request);
    public int register(String username, String password);
    public Company getCompany(int company_id);
    public ArrayList<Job> getJobs(int company_id);
    public ArrayList<Apply> getAppies(int company_id, String status);
    public int credit(int company_id);
    public int editInfo(Company company);
    public int updateJob(Job job);
    public Resume getResume(int id);
    public int accept(int id);
    public int refuse(int id);
    public int read(int id);
}
