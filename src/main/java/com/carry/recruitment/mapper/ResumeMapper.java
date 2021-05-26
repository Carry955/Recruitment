package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.EduExp;
import com.carry.recruitment.entity.Resume;
import com.carry.recruitment.entity.Skill;
import com.carry.recruitment.entity.WorkExp;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.ibatis.annotations.Mapper;

import java.sql.ResultSet;
import java.util.ArrayList;

@Mapper
public interface ResumeMapper {
    public int insert(Resume resume);
    public int insertEdu(EduExp eduExp);
    public int insertWork(WorkExp workExp);
    public int insertSkill(Skill skill);
    public int update(Resume resume);
    public int updateEdu(EduExp eduExp);
    public int updateWork(WorkExp workExp);
    public int updateSkill(Skill skill);
    public ArrayList<Resume> getResumes(String stu_id);
    public Resume getResume(int resume_id);
}
