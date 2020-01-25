package com.igorkhromov.hibernatevalidatorresterrors.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igorkhromov.hibernatevalidatorresterrors.model.dto.SignInEmailDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

	private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String BASE_URL = "http://localhost:";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * Testing validation of Request (JSON form)
	 * logs: validation error status and body
	 */
	@Test
	void whenJsonLoginFormFieldsInvalid_thenShouldReturnValidationErrors() {
		SignInEmailDto jsonBody = new SignInEmailDto();
		jsonBody.setEmail("some.email"); //Invalid value: @Email
		jsonBody.setPassword("pass"); //Invalid value: @Pattern(regexp="^[a-zA-Z0-9]{6,12}$")

		ResponseEntity<String> responseEntity = restTemplate
				.postForEntity(BASE_URL + port + "/user/email", jsonBody, String.class);

		assertEquals(400, responseEntity.getStatusCode().value());
		log.info("Response body: {}", responseEntity.getBody());
	}

	/**
	 * Testing validation of Request (HTML form)
	 * logs: validation error status and body
	 */
	@Test
	void whenHtmlFormFieldsInvalid_thenShouldReturnValidationErrors() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("firstName", "Jo"); //Invalid value: @Size(min=3)
		map.add("phoneNumber", "ABC"); //Invalid value: @Pattern(regexp="^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")

		HttpEntity<MultiValueMap<String, String>> htmlBody = new HttpEntity<>(map, headers);

		ResponseEntity<String> responseEntity = restTemplate
				.postForEntity(BASE_URL + port + "/user/sms", htmlBody, String.class);

		assertEquals(400, responseEntity.getStatusCode().value());
		log.info("Response body: {}", responseEntity.getBody());
	}

	/**
	 * Testing validation of PathVariable (Broken constrains)
	 * logs: validation error status and body
	 */
	@Test
	void whenPathVariableInvalid_thenShouldReturnValidationErrors() {
		long userId = 0; //Invalid: @Min(1)

		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity(BASE_URL + port + "/user/" + userId + "/email", String.class);

		assertEquals(500, responseEntity.getStatusCode().value());
		log.info("Response body: {}", responseEntity.getBody());
	}

	/**
	 * Testing validation of RequestParam (Broken constrains)
	 * logs: validation error status and body
	 */
	@Test
	void whenRequestParamInvalid_thenShouldReturnValidationErrors() {
		String firstName = "Jo"; //Invalid: @Length(min=3)

		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity(BASE_URL + port + "/users?first_name=" + firstName, String.class);

		assertEquals(500, responseEntity.getStatusCode().value());
		log.info("Response body: {}", responseEntity.getBody());
	}

	/**
	 * Testing validation of RequestParam (Broken constrains)
	 * logs: validation error status and body
	 */
	@Test
	void whenRequestParamNotMatchToExpectedType_thenShouldReturnValidationErrors() {
		String minAge = "teenager"; //Invalid value: conversion from String to Integer will fail

		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity(BASE_URL + port + "/editors?min_age=" + minAge, String.class);

		assertEquals(400, responseEntity.getStatusCode().value());
		log.info("Response body: {}", responseEntity.getBody());
	}

	/**
	 * Testing required RequestParam
	 * logs: validation error status and body
	 */
	@Test
	void whenRequiredRequestParamMissed_thenShouldReturnError() {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity(BASE_URL + port + "/editors", String.class);

		assertEquals(400, responseEntity.getStatusCode().value());
		log.info("Response body: {}", responseEntity.getBody());
	}

	/**
	 * Testing unhandled exception
	 * logs: validation error status and body
	 */
	@Test
	void whenUnhandledExceptionThrown_thenShouldReturnError() {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity(BASE_URL + port + "/exception", String.class);

		assertEquals(500, responseEntity.getStatusCode().value());
		log.info("Response body: {}", responseEntity.getBody());
	}
}