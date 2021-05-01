package com.carry.recruitment.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.carry.recruitment.entity.Credit;
import com.carry.recruitment.entity.Stu;
import com.carry.recruitment.entity.Teacher;
import com.carry.recruitment.mapper.StuMapper;
import com.carry.recruitment.service.SchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SchController {
    @Autowired
    private SchService schService;

    private boolean checkLogin(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("username")!=null){
            model.addAttribute("checked", true);
            return true;
        }else{
            model.addAttribute("checked", false);
            return false;
        }
    }

    @RequestMapping(value = "/sch", method = RequestMethod.GET)
    public String goSch(Model model){
        return "sch/login";
    }

    @RequestMapping(value = "/sch/login", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request){
        boolean checked = schService.login(new Teacher(request.getParameter("username"), request.getParameter("password")));
        if(checked){
            model.addAttribute("checked", true);
            request.getSession().setAttribute("username", request.getParameter("username"));
            return "redirect:/sch/stumng";
        }else{
            return "sch/login";
        }
    }

    @RequestMapping(value = "/sch/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request){
        model.addAttribute("checked", false);
        request.getSession().removeAttribute("username");
        return goSch(model);
    }

    @RequestMapping(value = "/sch/register", method = RequestMethod.POST)
    public String register(Model model, HttpServletRequest request){
        Teacher teacher = new Teacher(request.getParameter("username"), request.getParameter("password"));
        schService.register(teacher);
        return "sch/login";
    }

    @RequestMapping(value = "/sch/stumng", method = RequestMethod.GET)
    public String stumng(Model model, HttpServletRequest request){
        if(checkLogin(request, model)){
            ArrayList<Stu> stus = schService.getStus();
            model.addAttribute("stus", stus);
            return "sch/stumng";
        }else{
            return "redirect:/sch";
        }
    }
    @RequestMapping(value = "/sch/stuedit/{stu_id}", method = RequestMethod.GET)
    public String stuEdit(Model model, HttpServletRequest request, @PathVariable String stu_id){
        if(checkLogin(request, model)){
            model.addAttribute("editable", true);
            return stuInfo(model, request, stu_id);
        }else{
            return "redirect:/sch";
        }
    }
    @RequestMapping(value = "/sch/stuedit/{stu_id}", method = RequestMethod.POST)
    public String stuEdit_(Model model, HttpServletRequest request, @PathVariable String stu_id, Stu stu){
        if(checkLogin(request, model)){
            schService.editStu(stu);
            return stuEdit(model, request, stu_id);
        }else{
            return "direct:/sch";
        }
    }
    @RequestMapping(value = "/sch/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addStus(Model model, HttpServletRequest request, MultipartFile uploadFile) throws IOException {
        Map<String, Object> res = new HashMap<>();
        res.put("result", "success");
//        System.out.println("upload");
        EasyExcel.read(uploadFile.getInputStream(), Stu.class, new StuDataListener(schService)).sheet().doRead();
        return res;
    }

    @RequestMapping(value = "/sch/stuinfo/{stu_id}", method = RequestMethod.GET)
    public String stuInfo(Model model, HttpServletRequest request, @PathVariable String stu_id){
        if(checkLogin(request, model)){
            Stu stu = schService.getStu(stu_id);
            model.addAttribute("stu", stu);
            return "sch/stuinfo";
        }else{
            return "redirect:/sch";
        }
    }
    @RequestMapping(value = "/sch/addstu", method = RequestMethod.POST)
    public String addStu(Model model, HttpServletRequest request, Stu stu){
        if(checkLogin(request, model)){
            schService.addStu(stu);
            return "redirect:/sch/stumng";
        }else{
            return "redirect:/sch";
        }
    }

    @RequestMapping(value = "/sch/delstu/{stu_id}", method = RequestMethod.GET)
    public String delStu(Model model, HttpServletRequest request, @PathVariable String stu_id){
        if(checkLogin(request, model)){
            schService.delStu(stu_id);
            return "redirect:/sch/stumng";
        }else{
            return "redirect:/sch";
        }
    }

    @RequestMapping(value = "/sch/credit", method = RequestMethod.GET)
    public String credit(Model model, HttpServletRequest request){
        if(checkLogin(request, model)){
            ArrayList<Credit> credits_applying = schService.getCredits("申请中");
            ArrayList<Credit> credits_passed = schService.getCredits("已通过");
            model.addAttribute("credits_applying", credits_applying);
            model.addAttribute("credits_passed", credits_passed);
            return "sch/credit";
        }else{
            return "redirect:/sch";
        }
    }
    @RequestMapping(value = "/sch/credit/{credit_id}", method = RequestMethod.GET)
    public String credit(Model model, HttpServletRequest request, @PathVariable  int credit_id){
        if(checkLogin(request, model)){
            schService.credit(credit_id);
            return "redirect:/sch/credit";
        }else{
            return "redirect:/sch";
        }
    }
    @RequestMapping(value = "/sch/revoke/{credit_id}", method = RequestMethod.GET)
    public String revoke(Model model, HttpServletRequest request, @PathVariable int credit_id){
        if(checkLogin(request, model)){
            schService.revoke(credit_id);
            return "redirect:/sch/credit";
        }else{
            return "redirect:/sch";
        }
    }

    @RequestMapping(value = "/sch/analyser", method = RequestMethod.GET)
    public String analyser(Model model, HttpServletRequest request){
        if(checkLogin(request, model)){
            return "sch/analyser";
        }else{
            return "redirect:/sch";
        }
    }

    class StuDataListener extends AnalysisEventListener<Stu>{
        ArrayList<Stu> stus = new ArrayList<>();
        private SchService schService;
        public StuDataListener(SchService schService){
            this.schService = schService;
        }
        @Override
        public void invoke(Stu stu, AnalysisContext analysisContext) {
            stus.add(stu);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            schService.addStus(stus);
            stus.clear();
        }
    }
}
