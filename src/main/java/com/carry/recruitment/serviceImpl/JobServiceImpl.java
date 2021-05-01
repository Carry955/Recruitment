package com.carry.recruitment.serviceImpl;

import com.carry.recruitment.entity.Job;
import com.carry.recruitment.mapper.JobMapper;
import com.carry.recruitment.service.JobService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("jobService")
public class JobServiceImpl implements JobService {

    @Autowired
    JobMapper jobMapper;

    @Override
    public List<Job> getAllJobs() {
        return jobMapper.selectAll();
    }

    @Override
    public Job getJob(int job_id) {
        return jobMapper.getJob(job_id);
    }

    @Override
    public Map<String, Object> search(String keyword, String city, String category, int page, int rows) {
        Page pg = PageHelper.startPage(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("jobs", jobMapper.searchJobWithCom(keyword, city, category));
        map.put("total", pg.getPages());
        return map;
    }

    @Override
    public int addJob(Job job) {
        int res = jobMapper.addJob(job);
        jobMapper.addDesc(job.getId(), job.getDesc());
        jobMapper.addReq(job.getId(), job.getRequire());
        return res;
    }

    @Override
    public int delJob(int job_id) {
        jobMapper.delDesc(job_id);
        jobMapper.delReq(job_id);
        return jobMapper.delJob(job_id);
    }

}
