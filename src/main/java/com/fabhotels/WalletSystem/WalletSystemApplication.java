package com.fabhotels.WalletSystem;

import org.h2.server.web.WebServlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


/*
 * MAIN SPRING BOOT APPLICATION CLASS TO START THE APPLICATION
 * 
 */
@SpringBootApplication
@ComponentScan(basePackages= "com.fabhotels.api, com.fabhotels.dao, com.fabhotels.service, com.fabhotels.utility, com.fabhotels.security")
@EntityScan("com.fabhotels.entity")
public class WalletSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletSystemApplication.class, args);
	}
	
	//REGISTERING BEAN FOR EMBEDDED H2 DB TO ENABLE H2_CONSOLE
	@Bean
    ServletRegistrationBean<WebServlet> h2servletRegistration(){
        ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<>( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }


}
