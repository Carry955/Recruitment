package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.Hr;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HrMapper {
    public Hr login(String username);
}
