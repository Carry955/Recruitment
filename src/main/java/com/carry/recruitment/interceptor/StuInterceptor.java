package com.carry.recruitment.interceptor;

import com.carry.recruitment.entity.Stu;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StuInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            Stu stu = (Stu)request.getSession().getAttribute("stu");
            if(stu != null){
                return true;
            }
            response.sendRedirect("/");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getMethod().equals("POST")){
            return;
        }
        Stu stu = (Stu)request.getSession().getAttribute("stu");
        modelAndView.getModelMap().addAttribute("stu", stu);
    }
}
