package com.stackroute.usertrackservice.config;

import com.stackroute.usertrackservice.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public FilterRegistrationBean jwtFilter() {

        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JwtFilter());
        filterRegistrationBean.addUrlPatterns("/api/usertrackservice/user/*");
        return filterRegistrationBean;
    }
}
