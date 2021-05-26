package com.carry.recruitment.service;

import com.carry.recruitment.entity.Apply;
import com.carry.recruitment.entity.Credit;
import com.carry.recruitment.entity.Stu;
import com.carry.recruitment.entity.Teacher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

public interface SchService {
    public boolean login(Teacher teacher, HttpServletRequest request);
    public void logout(HttpServletRequest request);
    public int register(Teacher teacher);
    public ArrayList<Credit> getCredits(String status);
    public int credit(int credit_id);
    public int revoke(int credit_id);
    public ArrayList<Stu> getStus();
    public int addStu(Stu stu);
    public int delStu(String stu_id);
    public int editStu(Stu stu);
    public Stu getStu(String stu_id);
    public int addStus(ArrayList<Stu> stus);
    public ArrayList<ArrayList<Map<String, Object>>> getAnalysis();
}
