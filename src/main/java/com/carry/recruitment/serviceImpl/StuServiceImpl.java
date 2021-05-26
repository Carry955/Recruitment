package com.carry.recruitment.serviceImpl;

import com.carry.recruitment.entity.*;
import com.carry.recruitment.mapper.ApplyMapper;
import com.carry.recruitment.mapper.FavoriteMapper;
import com.carry.recruitment.mapper.ResumeMapper;
import com.carry.recruitment.mapper.StuMapper;
import com.carry.recruitment.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service("stuService")
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Autowired
    private ResumeMapper resumeMapper;

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public boolean login(Stu stu, HttpServletRequest request) {
        Stu mStu = stuMapper.getOne(stu.getId());
        if(mStu == null){
            return false;
        }
        if(stu.getPassword().equals(mStu.getPassword())){
            request.getSession().setAttribute("stu", mStu);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("stu");
    }

    @Override
    public ArrayList<Resume> getResume(String stu_id) {
        return resumeMapper.getResumes(stu_id);
    }

    @Override
    public int updateResume(String stu_id ,Resume resume) {
        if(resume.getId() == 0){
            int res = 0;
            resume.setAvator("/img/student.png");
            res += resumeMapper.insertEdu(resume.getEdus().get(0));
            res += resumeMapper.insertWork(resume.getWorks().get(0));
            res += resumeMapper.insertSkill(resume.getSkills().get(0));
            res += resumeMapper.insert(resume);
            return res;
        }else{
            int res = 0;
            res += resumeMapper.updateEdu(resume.getEdus().get(0));
            res += resumeMapper.updateWork(resume.getWorks().get(0));
            res += resumeMapper.updateSkill(resume.getSkills().get(0));
            res += resumeMapper.update(resume);
            return res;
        }
    }

    @Override
    public int apply(Apply apply) {
        return applyMapper.apply(apply);
    }

    @Override
    public int revoke(Apply apply) {
        return applyMapper.revoke(apply);
    }

    @Override
    public ArrayList<Apply> getapplys(String stu_id, String status) {
        return applyMapper.getApplys(stu_id, status);
    }

    @Override
    public ArrayList<Favorite> getFavorites(String stu_id) {
        return favoriteMapper.getAll(stu_id);
    }

    @Override
    public int addFavorite(Favorite favorite) {
        return favoriteMapper.addFav(favorite);
    }

    @Override
    public int delFavorite(Favorite favorite) {
        return favoriteMapper.delFav(favorite);
    }

}
