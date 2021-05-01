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

import java.util.ArrayList;

@Service("schService")
public class SchServiceImpl implements SchService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StuMapper stuMapper;

    @Override
    public boolean login(Teacher teacher) {
        Teacher mTeacher = teacherMapper.login(teacher.getUsername());
        if(mTeacher.getPassword().equals(teacher.getPassword())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int register(Teacher teacher) {
        return teacherMapper.register(teacher);
    }

    @Override
    public ArrayList<Credit> getCredits(String state) {
        return teacherMapper.getCredits(state);
    }

    @Override
    public int credit(int credit_id) {
        return teacherMapper.credit(credit_id);
    }

    @Override
    public int revoke(int credit_id) {
        System.out.println(credit_id);
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

}
