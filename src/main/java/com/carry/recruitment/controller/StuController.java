package com.carry.recruitment.controller;

import com.carry.recruitment.entity.*;
import com.carry.recruitment.service.JobService;
import com.carry.recruitment.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StuController {

    @Autowired
    private StuService stuService;
    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request){
        return "stu/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request, Stu stu){
        stuService.login(stu, request);
        return index(model, request);
    }

    @RequestMapping(value = "/logout", method=RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request){
        stuService.logout(request);
        return index(model, request);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Job> search(Model model, HttpServletRequest request){
        String keyword = request.getParameter("keyword");
        String city = request.getParameter("city");
        String category = request.getParameter("category");
        ArrayList<Job> jobs = jobService.search(keyword, city, category);
        return jobs;
    }

    @RequestMapping(value = "/detail/{job_id}", method = RequestMethod.GET)
    public String detail(Model model, @PathVariable int job_id, HttpServletRequest request){
        Job mJob = jobService.getJob(job_id);
        model.addAttribute("job", mJob);
        return "stu/jobDetail";
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public String getResume(Model model, HttpServletRequest request, Resume resume){
            Stu stu = (Stu)request.getSession().getAttribute("stu");
            ArrayList<Resume> resumes = stuService.getResume(stu.getId());
            model.addAttribute("resume", resumes.get(0));
            return "stu/myResume";
    }
    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    public String updateResume(Model model, Resume resume){
        stuService.updateResume(resume);
        return "redirect:/resume";
    }

    @RequestMapping(value="/applied", method = RequestMethod.GET)
    public String applied(Model model, HttpServletRequest request){
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        ArrayList<Apply> applys = stuService.getapplys(stu.getId());
        model.addAttribute("applys", applys);
        return "stu/myDelivery";
    }
    @RequestMapping(value = "/apply/{job_id}", method = RequestMethod.GET)
    public String apply(Model model, HttpServletRequest request, @PathVariable int job_id){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        String posttime = df.format(new Date());
        Resume resume = stuService.getResume(stu.getId()).get(0);
        Apply apply = new Apply(stu.getId(), posttime, "已投递", new Job(job_id), resume);
        stuService.apply(apply);
        return index(model, request);
    }
    @RequestMapping(value = "/revoke/{apply_id}", method = RequestMethod.GET)
    public String revoke(Model model, HttpServletRequest request, @PathVariable int apply_id){
        stuService.revoke(new Apply(apply_id));
        return applied(model, request);
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    public String favorite(Model model, HttpServletRequest request){
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        ArrayList<Favorite> favorites = stuService.getFavorites(stu.getId());
        model.addAttribute("favorites", favorites);
        return "stu/myFavorite";
    }
    @RequestMapping(value = "addfavorite/{job_id}", method = RequestMethod.GET)
    public String addFavorite(Model model, HttpServletRequest request,@PathVariable int job_id){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        String settime = df.format(new Date());
        Favorite favorite = new Favorite(stu.getId(), new Job(job_id), settime);
        stuService.addFavorite(favorite);
        return index(model, request);
    }

    @RequestMapping(value = "/delfavorite/{favorite_id}", method = RequestMethod.GET)
    public String delFavorite(Model model, HttpServletRequest request, @PathVariable int favorite_id){
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        Favorite favorite = new Favorite(favorite_id, stu.getId());
        stuService.delFavorite(favorite);
        return "redirect:/favorite";
    }
}
