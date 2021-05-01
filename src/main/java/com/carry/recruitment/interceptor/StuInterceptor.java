package com.carry.recruitment.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StuInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            String stu_id = (String)request.getSession().getAttribute("stu");
            if(stu_id != null){
                return true;
            }
            response.sendRedirect("/");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
