package com.carry.recruitment.interceptor;

import com.carry.recruitment.entity.Stu;
import org.springframework.web.servlet.HandlerInterceptor;

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
}
