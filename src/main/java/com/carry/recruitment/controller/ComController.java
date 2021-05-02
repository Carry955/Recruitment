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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(value = "/com", method = RequestMethod.GET)
    public String index(Model model){
        return "com/login";
    }

    @RequestMapping(value = "/com/login", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request, Hr hr){
        comService.login(hr, request);
        return "redirect:/com/cominfo";
    }
    @RequestMapping(value = "/com/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request){
        model.addAttribute("checked", false);
        request.getSession().removeAttribute("company_id");
        return "redirect:/com";
    }

    @RequestMapping(value = "/com/cominfo", method = RequestMethod.GET)
    public String comInfo(Model model, HttpServletRequest request){
        return "com/cominfo";
    }

    @RequestMapping(value = "/com/credit/{company_id}", method = RequestMethod.GET)
    public String apply(Model model, HttpServletRequest request, @PathVariable int company_id){
        comService.credit(company_id);
        return "redirect:/com/index";
    }
    @RequestMapping(value = "/com/editinfo/{company_id}", method = RequestMethod.POST)
    public String editInfo(Model model, HttpServletRequest request, @PathVariable int company_id, Company company){
        company.setId(company_id);
        comService.editInfo(company);
        return "redirect:/com/cominfo";
    }

    @RequestMapping(value = "/com/mng", method = RequestMethod.GET)
    public String mng(Model model, HttpServletRequest request){
        Hr hr = (Hr)request.getSession().getAttribute("hr");
        ArrayList<Job> jobs = comService.getJobs(hr.getCompany().getId());
        model.addAttribute("jobs", jobs);
        return "com/jobs";
    }
    @RequestMapping(value = "/com/addjob", method = RequestMethod.POST)
    public String addJob(Model model, HttpServletRequest request, Job job){
        String descs = request.getParameter("descs");
        String requires = request.getParameter("requires");
        List<String> desc = Arrays.asList(descs.split("\n"));
        List<String> require = Arrays.asList(requires.split("\n"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String settime = df.format(new Date());
        Hr hr = (Hr)request.getSession().getAttribute("hr");
        job.setDesc(desc);
        job.setRequire(require);
        job.setSettime(settime);
        job.setCompany(hr.getCompany());
        jobService.addJob(job);
        return mng(model, request);
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
    public String editJob_get(Model model, HttpServletRequest request, @PathVariable int job_id){
        Job job = jobService.getJob(job_id);
        String descs = StringUtils.join(job.getDesc(), "\n");
        String reqs = StringUtils.join(job.getRequire(), "\n");
        model.addAttribute("descs", descs);
        model.addAttribute("reqs", reqs);
        model.addAttribute("job", job);
        model.addAttribute("editable", true);
        return "com/jobDetail";
    }
    @RequestMapping(value = "/com/editjob/{job_id}", method = RequestMethod.POST)
    public String editJob(Model model, HttpServletRequest request, @PathVariable int job_id, Job job){
        String descs = request.getParameter("descs");
        String requires = request.getParameter("reqs");
        List<String> desc = Arrays.asList(descs.split("\n"));
        List<String> require = Arrays.asList(requires.split("\n"));
        job.setId(job_id);
        job.setDesc(desc);
        job.setRequire(require);
        comService.updateJob(job);
        return editJob_get(model, request, job_id);
    }

    @RequestMapping(value = "/com/posted", method = RequestMethod.GET)
    public String posted_get(Model model, HttpServletRequest request){
        return "com/posted";
    }

    @RequestMapping(value = "/com/posted", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Apply> posted(Model model, HttpServletRequest request){
        Hr hr = (Hr)request.getSession().getAttribute("hr");
        String status = request.getParameter("status");
        ArrayList<Apply> applys = comService.getAppies(hr.getCompany().getId(), status);
        System.out.println(applys);
        return applys;
    }
}
