package com.igorkhromov.hibernatevalidatorresterrors.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

	//Enables RequestParam and PathVariables validation in Controllers
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = jacksonMessageConverter.getObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		SimpleDateFormat dateFormat = new SimpleDateFormat(StdDateFormat.DATE_FORMAT_STR_ISO8601);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		mapper.setDateFormat(dateFormat);
		converters.add(jacksonMessageConverter);
	}
}