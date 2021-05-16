package com.carry.recruitment.config;

import com.carry.recruitment.interceptor.SchInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SchLoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new SchInterceptor());
        registration.addPathPatterns(
                "/sch/register",
                "/sch/stumng",
                "/sch/stuedit/*",
                "/sch/stuinfo/*",
                "/sch/delstu/*",
                "/sch/credit",
                "/sch/credit/*",
                "/sch/revoke/*",
                "/sch/analyser"
        );
    }
}
