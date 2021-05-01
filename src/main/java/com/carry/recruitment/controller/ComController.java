package com.carry.recruitment.controller;

import com.carry.recruitment.entity.Apply;
import com.carry.recruitment.entity.Company;
import com.carry.recruitment.entity.Hr;
import com.carry.recruitment.entity.Job;
import com.carry.recruitment.service.ComService;
import com.carry.recruitment.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ComController {
    @Autowired
    private ComService comService;

    @Autowired
    private JobService jobService;

    private boolean checkLogin(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("company_id")!=null){
            model.addAttribute("checked", true);
            return true;
        }else{
            model.addAttribute("checked", false);
            return false;
        }
    }

    @RequestMapping(value = "/com", method = RequestMethod.GET)
    public String goCom(Model model){
        return "com/login";
    }

    @RequestMapping(value = "/com/login", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request){
        int company_id = comService.login(new Hr(request.getParameter("username"), request.getParameter("password")));
        if(company_id != 0){
            model.addAttribute("checked", true);
            request.getSession().setAttribute("company_id", company_id);
            return "redirect:/com/index";
        }else{
            return "com/login";
        }
    }
    @RequestMapping(value = "/com/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request){
        model.addAttribute("checked", false);
        request.getSession().removeAttribute("company_id");
        return goCom(model);
    }

    @RequestMapping(value = "/com/index", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request){
        if(checkLogin(request, model)){
            int company_id = (int)request.getSession().getAttribute("company_id");
            Company company = comService.getCompany(company_id);
            model.addAttribute("company", company);
            return "com/index";
        }else{
            return goCom(model);
        }
    }
    @RequestMapping(value = "/com/credit/{company_id}", method = RequestMethod.GET)
    public String apply(Model model, HttpServletRequest request, @PathVariable int company_id){
        if(checkLogin(request, model)){
            comService.credit(company_id);
            return index(model, request);
        }else{
            return goCom(model);
        }
    }
    @RequestMapping(value = "/com/editinfo/{company_id}", method = RequestMethod.POST)
    public String editInfo(Model model, HttpServletRequest request, @PathVariable int company_id, Company company){
        if (checkLogin(request, model)){
            company.setId(company_id);
            comService.editInfo(company);
            return index(model,request);
        }else{
            return goCom(model);
        }
    }

    @RequestMapping(value = "/com/mng", method = RequestMethod.GET)
    public String mng(Model model, HttpServletRequest request){
        if (checkLogin(request, model)){
            ArrayList<Job> jobs = comService.getJobs((int)request.getSession().getAttribute("company_id"));
            model.addAttribute("jobs", jobs);
            return "com/jobs";
        }else{
            return goCom(model);
        }
    }
    @RequestMapping(value = "/com/addjob", method = RequestMethod.POST)
    public String addJob(Model model, HttpServletRequest request, Job job){
        if(checkLogin(request, model)){
            String descs = request.getParameter("descs");
            String requires = request.getParameter("requires");
            List<String> desc = Arrays.asList(descs.split("\n"));
            List<String> require = Arrays.asList(requires.split("\n"));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String settime = df.format(new Date());
            job.setDesc(desc);
            job.setRequire(require);
            job.setSettime(settime);
            job.setCompany(new Company((int)request.getSession().getAttribute("company_id")));
            jobService.addJob(job);
            return mng(model, request);
        }else{
            return goCom(model);
        }
    }

    @RequestMapping(value = "/com/deljob/{job_id}", method = RequestMethod.GET)
    public String delJob(Model model, HttpServletRequest request,@PathVariable int job_id){
        jobService.delJob(job_id);
        return mng(model, request);
    }
    @RequestMapping(value = "/com/jobinfo/{job_id}", method = RequestMethod.GET)
    public String jobInfo(Model model, HttpServletRequest request, @PathVariable int job_id){
        Job job = jobService.getJob(job_id);
        String descs = StringUtils.join(job.getDesc(), "\n");
        String reqs = StringUtils.join(job.getRequire(), "\n");
        model.addAttribute("descs", descs);
        model.addAttribute("reqs", reqs);
        model.addAttribute("job", job);
        return "com/jobDetail";
    }

    @RequestMapping(value = "/com/editjob/{job_id}", method = RequestMethod.GET)
    public String editJob(Model model, HttpServletRequest request, @PathVariable int job_id){
        if (checkLogin(request, model)){
            Job job = jobService.getJob(job_id);
            String descs = StringUtils.join(job.getDesc(), "\n");
            String reqs = StringUtils.join(job.getRequire(), "\n");
            model.addAttribute("descs", descs);
            model.addAttribute("reqs", reqs);
            model.addAttribute("job", job);
            model.addAttribute("editable", true);
            return "com/jobDetail";
        }else{
            return goCom(model);
        }
    }
    @RequestMapping(value = "/com/editjob/{job_id}", method = RequestMethod.POST)
    public String editJob_(Model model, HttpServletRequest request, @PathVariable int job_id, Job job){
        if(checkLogin(request, model)){
            String descs = request.getParameter("descs");
            String requires = request.getParameter("reqs");
            List<String> desc = Arrays.asList(descs.split("\n"));
            List<String> require = Arrays.asList(requires.split("\n"));
            job.setId(job_id);
            job.setDesc(desc);
            job.setRequire(require);
            comService.updateJob(job);
            return editJob(model, request, job_id);
        }else{
            return goCom(model);
        }
    }

    @RequestMapping(value = "/com/posted", method = RequestMethod.GET)
    public String posted(Model model, HttpServletRequest request){
        if(checkLogin(request, model)){
            ArrayList<Apply> applies = comService.getAppies((int)request.getSession().getAttribute("company_id"));
            model.addAttribute("applies", applies);
            return "com/posted";
        }else{
            return goCom(model);
        }
    }
}
