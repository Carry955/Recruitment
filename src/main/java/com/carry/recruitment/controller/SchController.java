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
import org.springframework.lang.Nullable;
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

    @RequestMapping(value = "/sch", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request){
        return "/sch/login";
    }

    @RequestMapping(value = "/sch/login", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request, Teacher teacher){
        if(schService.login(teacher, request)){
            return "redirect:/sch/stumng";
        }else{
            model.addAttribute("msg", "用户名或密码错误！");
            return index(model, request);
        }
    }

    @RequestMapping(value = "/sch/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request){
        schService.logout(request);
        return "redirect:/sch";
    }

    @RequestMapping(value = "/sch/register", method = RequestMethod.POST)
    public String register(Model model, HttpServletRequest request, Teacher teacher){
        schService.register(teacher);
        return "redirect:/sch";
    }

    @RequestMapping(value = "/sch/stumng", method = RequestMethod.GET)
    public String stumng(Model model, HttpServletRequest request){
        ArrayList<Stu> stus = schService.getStus();
        model.addAttribute("stus", stus);
        return "sch/stumng";
    }
    @RequestMapping(value = "/sch/stuedit/{stu_id}", method = RequestMethod.GET)
    public String stuEdit(Model model, HttpServletRequest request, @PathVariable String stu_id){
        model.addAttribute("editable", true);
        return stuInfo(model, request, stu_id);
    }
    @RequestMapping(value = "/sch/stuedit/{stu_id}", method = RequestMethod.POST)
    public String stuEdit_(Model model, HttpServletRequest request, @PathVariable String stu_id, Stu stu){
        schService.editStu(stu);
        return stuEdit(model, request, stu_id);
    }
    @RequestMapping(value = "/sch/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addStus(Model model, HttpServletRequest request, MultipartFile uploadFile) throws IOException {
        Map<String, Object> res = new HashMap<>();
        res.put("result", "success");
        EasyExcel.read(uploadFile.getInputStream(), Stu.class, new StuDataListener(schService)).sheet().doRead();
        return res;
    }

    @RequestMapping(value = "/sch/stuinfo/{stu_id}", method = RequestMethod.GET)
    public String stuInfo(Model model, HttpServletRequest request, @PathVariable String stu_id){
        Stu stu = schService.getStu(stu_id);
        model.addAttribute("stu", stu);
        return "sch/stuinfo";
    }
    @RequestMapping(value = "/sch/addstu", method = RequestMethod.POST)
    public String addStu(Model model, HttpServletRequest request, Stu stu){
        schService.addStu(stu);
        return "redirect:/sch/stumng";
    }

    @RequestMapping(value = "/sch/delstu/{stu_id}", method = RequestMethod.GET)
    public String delStu(Model model, HttpServletRequest request, @PathVariable String stu_id){
        schService.delStu(stu_id);
        return "redirect:/sch/stumng";
    }

    @RequestMapping(value = "/sch/credit", method = RequestMethod.GET)
    public String credit(Model model, HttpServletRequest request){
        return "sch/credit";
    }

    @RequestMapping(value = "/sch/credit", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Credit> credit_get(Model model, HttpServletRequest request){
        String status = request.getParameter("status");
        ArrayList<Credit> credits = schService.getCredits(status);
        return credits;
    }

    @RequestMapping(value = "/sch/credit/{credit_id}", method = RequestMethod.GET)
    public String credit(Model model, HttpServletRequest request, @PathVariable  int credit_id){
        schService.credit(credit_id);
        return "redirect:/sch/credit";
    }
    @RequestMapping(value = "/sch/revoke/{credit_id}", method = RequestMethod.GET)
    public String revoke(Model model, HttpServletRequest request, @PathVariable int credit_id){
        schService.revoke(credit_id);
        return "redirect:/sch/credit";
    }

    @RequestMapping(value = "/sch/analyser", method = RequestMethod.GET)
    public String analyser_get(Model model, HttpServletRequest request){
        return "sch/analyser";
    }
    @RequestMapping(value = "/sch/analyser", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<ArrayList<Map<String, Object>>> analyser(Model model, HttpServletRequest request){
        return schService.getAnalysis();
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
