package woodo.practice.authservice.global.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Project        : springboot-microservices
 * DATE           : 2025. 1. 2.
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 1. 2.      dnejdzlr2          최초 생성
 */
class JwtUtilTest {

	@Test
	void testGenerateToken() {
		// Given: 사용자 이름 설정
		String username = "test_user";

		// When: 토큰 생성
		String token = JwtUtil.generateToken(username);

		// Then: 생성된 토큰이 null이 아니고 비어있지 않은지 확인
		assertNotNull(token, "Generated token should not be null");
		assertFalse(token.isEmpty(), "Generated token should not be empty");
		System.out.println("Generated Token: " + token);
	}

	@Test
	void testValidateToken() {
		// Given: 사용자 이름 설정 및 토큰 생성
		String username = "test_user";
		String token = JwtUtil.generateToken(username);

		// When: 토큰을 검증하여 클레임 추출
		Claims claims = JwtUtil.validateToken(token);

		// Then: 클레임에서 사용자 이름이 예상대로 설정되었는지 확인
		assertNotNull(claims, "Claims should not be null");
		assertEquals(username, claims.getSubject(), "Subject in claims should match the username");
		System.out.println("Parsed Claims: " + claims.getSubject());
	}

	@Test
	void testExpiredToken() {
		// Given: 만료된 토큰을 생성하기 위해 만료 시간을 1초로 변경
		String expiredToken = Jwts.builder()
			.setSubject("expired_user")
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000)) // 1초 후 만료
			.signWith(JwtUtil.getSecretKey(), SignatureAlgorithm.HS256)
			.compact();

		// Sleep for 2초로 토큰 만료 대기
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// When & Then: 만료된 토큰을 검증할 때 예외 발생 확인
		assertThrows(ExpiredJwtException.class, () -> JwtUtil.validateToken(expiredToken), "ExpiredJwtException should be thrown for expired tokens");
	}
}