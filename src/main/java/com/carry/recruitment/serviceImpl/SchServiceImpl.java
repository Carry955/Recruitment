package com.carry.recruitment.serviceImpl;

import com.carry.recruitment.entity.Apply;
import com.carry.recruitment.entity.Credit;
import com.carry.recruitment.entity.Stu;
import com.carry.recruitment.entity.Teacher;
import com.carry.recruitment.mapper.ApplyMapper;
import com.carry.recruitment.mapper.StuMapper;
import com.carry.recruitment.mapper.TeacherMapper;
import com.carry.recruitment.service.SchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@Service("schService")
public class SchServiceImpl implements SchService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StuMapper stuMapper;

    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public boolean login(Teacher teacher, HttpServletRequest request) {
        Teacher mTeacher = teacherMapper.getOne(teacher.getUsername());
        if(mTeacher == null){
            return false;
        }
        if(teacher.getPassword().equals(mTeacher.getPassword())){
            request.getSession().setAttribute("teacher", mTeacher);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("teacher");
    }

    @Override
    public int register(Teacher teacher) {
        teacher.setImg("/img/teacher");
        return teacherMapper.register(teacher);
    }

    @Override
    public ArrayList<Credit> getCredits(String status) {
        return teacherMapper.getCredits(status);
    }

    @Override
    public int credit(int credit_id) {
        return teacherMapper.credit(credit_id);
    }

    @Override
    public int revoke(int credit_id) {
        return teacherMapper.revoke(credit_id);
    }

    @Override
    public ArrayList<Stu> getStus() {
        return stuMapper.getAll();
    }

    @Override
    public int addStu(Stu stu) {
        return stuMapper.add(stu);
    }

    @Override
    public int delStu(String stu_id) {
        return stuMapper.del(stu_id);
    }

    @Override
    public int editStu(Stu stu) {
        return stuMapper.edit(stu);
    }

    @Override
    public Stu getStu(String stu_id) {
        return stuMapper.getOne(stu_id);
    }

    @Override
    public int addStus(ArrayList<Stu> stus) {
        return stuMapper.batchInsert(stus);
    }

    @Override
    public ArrayList<ArrayList<Map<String, Object>>> getAnalysis() {
        ArrayList<ArrayList<Map<String, Object>>> list = new ArrayList<>();
        list.add(applyMapper.getCategoryNum());
        list.add(applyMapper.getMajorNum());
        return list;
    }

}
