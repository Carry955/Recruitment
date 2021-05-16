package com.carry.recruitment.interceptor;

import com.carry.recruitment.entity.Teacher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SchInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
            if(teacher != null){
                return true;
            }
            response.sendRedirect("/sch");
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
        Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
        modelAndView.getModelMap().addAttribute("teacher", teacher);
    }
}
