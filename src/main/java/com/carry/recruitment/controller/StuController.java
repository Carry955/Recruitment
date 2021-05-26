package com.carry.recruitment.controller;

import com.carry.recruitment.entity.*;
import com.carry.recruitment.service.JobService;
import com.carry.recruitment.service.StuService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class StuController {

    @Autowired
    private StuService stuService;
    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request){
        try{
            Stu stu = (Stu)request.getSession().getAttribute("stu");
            if(stu != null){
                model.addAttribute("stu", stu);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "stu/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Boolean> login(Model model, HttpServletRequest request, Stu stu){
        String referPath = request.getHeader("referer").replace("http://www.job.com", "");
        Map map = new HashMap();
        map.put("status", stuService.login(stu, request));
        return map;
    }

    @RequestMapping(value = "/logout", method=RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request){
        stuService.logout(request);

        return "redirect:/";
    }

    @RequestMapping(value = "/search/{page}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> search(Model model, HttpServletRequest request, @PathVariable int page){
        String keyword = request.getParameter("keyword");
        String city = request.getParameter("city");
        String category = request.getParameter("category");
        Map<String, Object> map = jobService.search(keyword, city, category, page, 10);
        return map;
    }

    @RequestMapping(value = "/detail/{job_id}", method = RequestMethod.GET)
    public String detail(Model model, @PathVariable int job_id, HttpServletRequest request){
        Job mJob = jobService.getJob(job_id);
        model.addAttribute("job", mJob);
        try{
            Stu stu = (Stu)request.getSession().getAttribute("stu");
            if(stu != null){
                model.addAttribute("stu", stu);
                if(jobService.isApplied(stu.getId(), job_id)){
                    model.addAttribute("isApplied", true);
                }
                if(jobService.isFavorited(stu.getId(), job_id)){
                    model.addAttribute("isFavorited", true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "stu/jobDetail";
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public String resume(Model model, HttpServletRequest request){
        return "stu/myResume";
    }
    @RequestMapping(value = "/getresume", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Resume> getResume(HttpServletRequest request){
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        return stuService.getResume(stu.getId());
    }

    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    @ResponseBody
    public Map updateResume(Model model, HttpServletRequest request, @RequestBody Resume resume){
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        stuService.updateResume(stu.getId(), resume);
        Map map = new HashMap();
        map.put("status", "success");
        return map;
    }
    @RequestMapping(value = "/applied", method = RequestMethod.GET)
    public String applied_get(Model model, HttpServletRequest request){
        return "stu/myDelivery";
    }

    @RequestMapping(value="/applied", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Apply> applied(Model model, HttpServletRequest request){
        ArrayList<Apply> applys = new ArrayList<>();
        String status = request.getParameter("status");
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        if(status.equals("已处理")){
            applys.addAll(stuService.getapplys(stu.getId(), "已录取"));
            applys.addAll(stuService.getapplys(stu.getId(), "已拒绝"));
        }else{
            applys.addAll(stuService.getapplys(stu.getId(), status));
        }
        return applys;
    }
    @RequestMapping(value = "/apply/{job_id}", method = RequestMethod.GET)
    public String apply(Model model, HttpServletRequest request, @PathVariable int job_id){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        String posttime = df.format(new Date());
        ArrayList<Resume> resumes = stuService.getResume(stu.getId());
        if(resumes.size()!=0){
            Resume resume = resumes.get(0);
            Apply apply = new Apply(stu.getId(), posttime, "已投递", new Job(job_id), resume);
            stuService.apply(apply);
        }
        String referPath = request.getHeader("referer").replace("http://www.job.com", "");
        return "redirect:"+referPath;
//        return index(model, request);
    }
    @RequestMapping(value = "/revoke/{apply_id}", method = RequestMethod.GET)
    public String revoke(Model model, HttpServletRequest request, @PathVariable int apply_id){
        stuService.revoke(new Apply(apply_id));
        return "redirect:/applied";
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    public String favorite(Model model, HttpServletRequest request){
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        ArrayList<Favorite> favorites = stuService.getFavorites(stu.getId());
        model.addAttribute("favorites", favorites);
        return "stu/myFavorite";
    }
    @RequestMapping(value = "/addfavorite/{job_id}", method = RequestMethod.GET)
    public String addFavorite(Model model, HttpServletRequest request,@PathVariable int job_id){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        String settime = df.format(new Date());
        Favorite favorite = new Favorite(stu.getId(), new Job(job_id), settime);
        stuService.addFavorite(favorite);
        String referPath = request.getHeader("referer").replace("http://www.job.com", "");
        return "redirect:"+referPath;
    }

    @RequestMapping(value = "/delfavorite/{favorite_id}", method = RequestMethod.GET)
    public String delFavorite(Model model, HttpServletRequest request, @PathVariable int favorite_id){
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        Favorite favorite = new Favorite(favorite_id, stu.getId());
        stuService.delFavorite(favorite);
        return favorite(model,request);
    }
}
