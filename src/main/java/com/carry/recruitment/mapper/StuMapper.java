package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.Stu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Mapper
public interface StuMapper {
    public ArrayList<Stu> getAll();
    public int add(Stu stu);
    public int del(String stu_id);
    public int edit(Stu stu);
    public Stu getOne(String stu_id);
    public int batchInsert(ArrayList<Stu> stus);
}
