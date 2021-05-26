package com.carry.recruitment.controller;

import com.baidu.nlp.LAC;
import com.carry.recruitment.entity.*;
import com.carry.recruitment.mapper.ApplyMapper;
import com.carry.recruitment.service.ComService;
import com.carry.recruitment.service.JobService;
import com.carry.recruitment.util.LacUtil;
import org.apache.ibatis.annotations.Param;
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
        if(comService.login(hr, request)){
            return "redirect:/com/cominfo";
        }else{
            model.addAttribute("msg", "用户名或密码错误");
            return index(model);
        }
    }
    @RequestMapping(value = "/com/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request){
        model.addAttribute("checked", false);
        request.getSession().removeAttribute("company_id");
        return "redirect:/com";
    }

    @RequestMapping(value = "/com/register", method = RequestMethod.POST)
    public Map<String, String> register(Model model, HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, String> map = new HashMap<>();
        map.put("status", "success");
        return map;
    }

    @RequestMapping(value = "/com/cominfo", method = RequestMethod.GET)
    public String comInfo(Model model, HttpServletRequest request){
        Hr hr = (Hr)request.getSession().getAttribute("hr");
        Company company = comService.getCompany(hr.getCompany().getId());
        model.addAttribute("company", company);
        hr.setCompany(company);
        request.getSession().setAttribute("hr", hr);
        return "com/cominfo";
    }

    @RequestMapping(value = "/com/credit/{company_id}", method = RequestMethod.GET)
    public String apply(Model model, HttpServletRequest request, @PathVariable int company_id){
        comService.credit(company_id);
        return "redirect:/com/cominfo";
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
    public Map<String, ArrayList> posted(Model model, HttpServletRequest request){
        Hr hr = (Hr)request.getSession().getAttribute("hr");
        String status = request.getParameter("status");
        Map<String, ArrayList> map = new HashMap<>();
        ArrayList<Apply> applys = comService.getAppies(hr.getCompany().getId(), status);
        map.put("applys", applys);
        ArrayList<ArrayList> words = new ArrayList<>();
        for(Apply item: applys){
            if(item.getStatus().equals("已投递")){
                comService.read(item.getId());
            }
            ArrayList<String> word = new ArrayList<>();
            Lac lac = LacUtil.run(item.getResume().toString());
            for(int i=0; i<lac.getTags().size(); i++){
                if(lac.getTags().get(i).equals("ORG")
                        || lac.getTags().get(i).equals("nz")
                        || lac.getTags().get(i).equals("/honor")
                        || lac.getTags().get(i).equals("/political")){
                    word.add(lac.getWords().get(i));
                }
            }
            words.add(word);
        }
        map.put("words", words);
        return map;
    }

    @RequestMapping(value = "/com/resume", method = RequestMethod.POST)
    @ResponseBody
    public Resume resumeDetail(Model model, HttpServletRequest request, @Param("id") int id ){
        Resume resume = comService.getResume(id);
        return resume;
    }

    @RequestMapping(value = "/com/accept", method = RequestMethod.POST)
    @ResponseBody
    public Map accept(Model model, HttpServletRequest request, @Param("id") int id){
        comService.accept(id);
        Map<String, String> res = new HashMap<>();
        res.put("status", "success");
        return res;
    }

    @RequestMapping(value = "com/refuse", method = RequestMethod.POST)
    @ResponseBody
    public Map refuse(Model model, HttpServletRequest request, @Param("id") int id){
        comService.refuse(id);
        Map<String, String> res = new HashMap<>();
        res.put("status", "success");
        return res;
    }
}
