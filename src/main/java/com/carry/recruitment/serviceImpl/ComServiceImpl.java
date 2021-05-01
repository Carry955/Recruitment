package com.carry.recruitment.serviceImpl;

import com.carry.recruitment.entity.Apply;
import com.carry.recruitment.entity.Company;
import com.carry.recruitment.entity.Hr;
import com.carry.recruitment.entity.Job;
import com.carry.recruitment.mapper.ApplyMapper;
import com.carry.recruitment.mapper.CompanyMapper;
import com.carry.recruitment.mapper.HrMapper;
import com.carry.recruitment.mapper.JobMapper;
import com.carry.recruitment.service.ComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("comService")
public class ComServiceImpl implements ComService {

    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public int login(Hr hr) {
        Hr mHr = hrMapper.login(hr.getUsername());
        if (mHr.getPassword().equals(hr.getPassword())){
            return mHr.getCompany().getId();
        }else{
            return 0;
        }
    }

    @Override
    public Company getCompany(int company_id) {
        return companyMapper.getCompany(company_id);
    }

    @Override
    public ArrayList<Job> getJobs(int company_id) {
        return jobMapper.getJobsByComId(company_id);
    }

    @Override
    public ArrayList<Apply> getAppies(int company_id) {
        return applyMapper.getAppliesByComId(company_id);
    }

    @Override
    public int credit(int company_id) {
        return companyMapper.credit(company_id);
    }

    @Override
    public int editInfo(Company company) {
        return companyMapper.edit(company);
    }

    @Override
    public int updateJob(Job job) {
            jobMapper.delDesc(job.getId());
            jobMapper.delReq(job.getId());
            jobMapper.addDesc(job.getId(), job.getDesc());
            jobMapper.addReq(job.getId(), job.getRequire());
        return jobMapper.updateJob(job);
    }
}
