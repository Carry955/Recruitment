package com.carry.recruitment.interceptor;

import com.carry.recruitment.entity.Company;
import com.carry.recruitment.entity.Hr;
import com.carry.recruitment.service.ComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ComAuthIntercepter implements HandlerInterceptor {

    @Autowired
    ComService comService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            Hr hr = (Hr)request.getSession().getAttribute("hr");
            if(hr.getCompany().getCredit().equals("已授权")){
                return true;
            }
            response.sendRedirect("/com/cominfo");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
