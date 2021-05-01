package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.Job;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface JobMapper {
    public List<Job> selectAll();
    public ArrayList<Job> getJobsByComId(int company_id);
    public Job getJob(int job_id);
    public ArrayList<Job> searchJob(String keyword, String city, String category);
    public int addJob(Job job);
    public int addDesc(int job_id, List<String> descs);
    public int addReq(int job_id, List<String> reqs);
    public int delJob(int job_id);
    public int delDesc(int job_id);
    public int delReq(int job_id);
    public int updateJob(Job job);
}
