package com.carry.recruitment.config;

import com.carry.recruitment.interceptor.ComAuthIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ComAuthConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new ComAuthIntercepter());
        registration.addPathPatterns(
                "/com/mng",
                "/com/addjob",
                "/com/deljob/*",
                "/com/jobinfo/*",
                "/com/editjob/*",
                "/com/editjob/*",
                "/com/posted"
        );
    }
}
