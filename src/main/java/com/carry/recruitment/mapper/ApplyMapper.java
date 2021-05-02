package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.Apply;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ApplyMapper {
    public ArrayList<Apply> getApplys(String stu_id, String status);
    public ArrayList<Apply> getAppliesByComId(int company_id, String status);
    public int apply(Apply apply);
    public int revoke(Apply apply);
}
