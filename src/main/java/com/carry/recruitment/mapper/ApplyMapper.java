package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.Apply;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ApplyMapper {
    public ArrayList<Apply> getApplys(String stu_id, String status);
    public ArrayList<Apply> getAppliesByComId(int company_id, String status);
    public int apply(Apply apply);
    public int revoke(Apply apply);
    public int setStatus(int id, String status);
    public ArrayList<Apply> getApplyByStuId(String stu_id, int job_id);
    public ArrayList<Map<String, Object>> getCategoryNum();
    public ArrayList<Map<String, Object>> getMajorNum();
}
