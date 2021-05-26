package com.carry.recruitment.config;

import com.carry.recruitment.interceptor.ComInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ComLoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new ComInterceptor());
        registration.addPathPatterns(
            "/com/index",
            "/com/cominfo",
            "/com/credit/*",
            "/com/editinfo/*",
            "/com/mng",
            "/com/addjob",
            "/com/deljob/*",
            "/com/jobinfo/*",
            "/com/editjob/*",
            "/com/editjob/*",
            "/com/posted",
            "/com/accept",
            "/com/refuse",
            "/com/read"
        );
    }
}
