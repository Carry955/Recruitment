package com.carry.recruitment.service;

import com.carry.recruitment.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.FactoryConfigurationError;
import java.util.ArrayList;


public interface StuService {
    public boolean login(Stu stu, HttpServletRequest request);
    public void logout(HttpServletRequest request);
    public ArrayList<Resume> getResume(String stu_id);
    public int updateResume(String stu_id, Resume resume);
    public int apply(Apply apply);
    public int revoke(Apply apply);
    public ArrayList<Apply> getapplys(String stu_id, String status);
    public ArrayList<Favorite> getFavorites(String stu_id);
    public int addFavorite(Favorite favorite);
    public int delFavorite(Favorite favorite);
}
