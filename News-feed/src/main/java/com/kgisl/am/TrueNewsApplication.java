package com.kgisl.am;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.kgisl.am.sso.JwtFilter;

@SpringBootApplication
public class TrueNewsApplication {

    public static void main(String[] args) throws Exception {
		SpringApplication.run(TrueNewsApplication.class, args);
    }
    
    @Value("${services.auth}")
    private String authService;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.setInitParameters(Collections.singletonMap("services.auth", authService));
        registrationBean.addUrlPatterns("/news", "/logout");

        return registrationBean;
    }
    
    @Bean()
    public DataSource dataSourceMultiTenant() {
	    return new MultiTenantAwareRoutingSource();
    }

}
