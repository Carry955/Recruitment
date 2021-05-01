package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.Resume;
import org.apache.ibatis.annotations.Mapper;

import java.sql.ResultSet;
import java.util.ArrayList;

@Mapper
public interface ResumeMapper {
    public int insert(Resume resume);
    public int update(Resume resume);
    public ArrayList<Resume> getResumes(String stu_id);
    public Resume getResume(int resume_id);
}
