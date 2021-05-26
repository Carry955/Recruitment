package com.carry.recruitment.serviceImpl;

import com.carry.recruitment.entity.*;
import com.carry.recruitment.mapper.*;
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

    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public boolean login(Hr hr, HttpServletRequest request) {
        Hr mHr = hrMapper.getOne(hr.getUsername());
        if(mHr == null){
            return false;
        }
        if (hr.getPassword().equals(mHr.getPassword())){
            request.getSession().setAttribute("hr", mHr);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int register(String username, String password) {
        Company company = new Company();
        return hrMapper.addOne(username, password);
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

    @Override
    public Resume getResume(int id) {
        return resumeMapper.getResume(id);
    }

    @Override
    public int accept(int id) {
        return applyMapper.setStatus(id, "已录取");
    }

    @Override
    public int refuse(int id) {
        return applyMapper.setStatus(id, "已拒绝");
    }

    public int read(int id){
        return applyMapper.setStatus(id, "已阅读");
    }
}
