package com.carry.recruitment.config;

import com.carry.recruitment.interceptor.StuInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.PathMatcher;

@Configuration
public class StuLoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new StuInterceptor());
        registration.addPathPatterns(
                "/resume",
                "/applied",
                "/apply/*",
                "/addfavorite/*",
                "/revoke/*",
                "/favorite",
                "/addfavorite/*",
                "/delfavorite/*"
        );
    }
}
