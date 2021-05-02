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

import javax.servlet.http.HttpServletRequest;
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
    public boolean login(Hr hr, HttpServletRequest request) {
        Hr mHr = hrMapper.getOne(hr.getUsername());
        if (hr.getPassword().equals(mHr.getPassword())){
            request.getSession().setAttribute("hr", mHr);
            return true;
        }else{
            return false;
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
    public ArrayList<Apply> getAppies(int company_id, String status) {
        return applyMapper.getAppliesByComId(company_id, status);
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
