package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.Credit;
import com.carry.recruitment.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface TeacherMapper {
    public Teacher login(String username);
    public int register(Teacher teacher);
    public ArrayList<Credit> getCredits(String state);
    public int credit(int credit_id);
    public int revoke(int credit_id);
}
