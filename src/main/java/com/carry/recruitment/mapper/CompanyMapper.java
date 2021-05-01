package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.Company;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {
    public Company getCompany(int company_id);
    public int credit(int company_id);
    public int edit(Company company);
}
