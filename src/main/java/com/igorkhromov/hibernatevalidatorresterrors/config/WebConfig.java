package com.igorkhromov.hibernatevalidatorresterrors.config;

import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan("com.igorkhromov.hibernatevalidatorresterrors")
public class WebConfig implements WebMvcConfigurer {

	// Enables RequestParam and PathVariables validation in Controllers
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
}