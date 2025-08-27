package com.trod.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trod.config.JwtConfig;
import jakarta.servlet.http.Cookie;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TrodApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrodApplicationTests {
	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper; // Spring Boot 會自動提供

	@Autowired
	JwtConfig jwtConfig;

	Cookie jwtCookie;

	@BeforeAll
	void login() throws Exception {
		String json = "{\"username\":\"root\",\"password\":\"123456789\"}";
		MvcResult res = mvc.perform(
				post("/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
			.andExpect(status().isAccepted())
			.andReturn();
		Cookie[] cookies = res.getResponse().getCookies();
		assertThat(cookies).isNotEmpty();
		jwtCookie = Arrays.stream(cookies)
			.filter(c -> jwtConfig.getKeyName().equals(c.getName()))
			.findFirst()
			.orElseThrow(Exception::new);
	}

	@Test
	void createCard() throws Exception {
		String json = "{\"name\":\"test\",\"description\":\"\",\"cost\":0,\"health\":10,\"mana\":0}";
		MvcResult postResult = mvc.perform(
				post("/card/mainCharacter")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					.cookie(jwtCookie))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").exists())
			.andReturn();

		String postJson = postResult.getResponse().getContentAsString();
		JsonNode root = objectMapper.readTree(postJson);
		String uuid = root.get("uuid").asText();
		assertThat(uuid).isNotEmpty();

		mvc.perform(get("/card/mainCharacter/{uuid}", uuid))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").exists());
	}

}
