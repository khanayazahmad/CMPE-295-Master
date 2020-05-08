package com.sjsu.masters.mediauploadservice.config;


import org.springframework.boot.web.servlet.*;
import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;
import org.springframework.web.filter.*;

@Configuration
public class CORSAdvice {

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}
