package com.carry.recruitment.interceptor;

import com.carry.recruitment.entity.Hr;
import com.carry.recruitment.mapper.CompanyMapper;
import com.carry.recruitment.service.ComService;
import com.carry.recruitment.serviceImpl.ComServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ComInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            Hr hr = (Hr)request.getSession().getAttribute("hr");
            if(hr != null){
                return true;
            }
            response.sendRedirect("/com");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView == null){
            return;
        }
        Hr hr = (Hr)request.getSession().getAttribute("hr");
        if(modelAndView.getModelMap().getAttribute("company") == null){
            modelAndView.getModelMap().addAttribute("company", hr.getCompany());
        }
    }
}
