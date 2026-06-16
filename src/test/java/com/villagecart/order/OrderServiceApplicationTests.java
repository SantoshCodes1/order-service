package com.villagecart.order;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.security.Key;
import java.util.Date;

@SpringBootTest
class OrderServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void generateTestToken() {

		// 🚨 CRITICAL: Use the EXACT SAME secret key string
		// you put inside your JwtUtil.java
		String secretKeyString =
				"M5D7d6Q1s7n9zY2m4R8YhKkVfU1uN3bJQ0xP7cWgL9A=";

		Key key = Keys.hmacShaKeyFor(secretKeyString.getBytes());

		// Build a mock token valid for 1 hour
		// for user "interview_candidate"
		String token = Jwts.builder()
				.subject("interview_candidate")
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
				.signWith(key)
				.compact();

		System.out.println(
				"\n================ COPY YOUR POSTMAN MOCK JWT TOKEN ================"
		);
		System.out.println(token);
		System.out.println(
				"==================================================================\n"
		);
	}
}
