package woodo.practice.authservice.global.util;

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

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

	// 사용자 정의 키
	private static final String SECRET_STRING = "woodo-secret"; // 원하는 문자열 입력
	private static final Key SECRET_KEY = generateKey(SECRET_STRING);
	private static final long EXPIRATION_TIME = 3600000; // 1시간


	public static Key getSecretKey() {
		return SECRET_KEY;
	}

	/**
	 * 사용자 정의 문자열을 기반으로 256비트 키 생성
	 */
	private static Key generateKey(String secret) {
		try {
			// SHA-256 해시 계산
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedKey = digest.digest(secret.getBytes(StandardCharsets.UTF_8));

			// 256비트 키 생성
			return new SecretKeySpec(hashedKey, SignatureAlgorithm.HS256.getJcaName());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 algorithm not available", e);
		}
	}


	// JWT 토큰 생성
	public static String generateToken(String username) {
		return Jwts.builder()
			.setSubject(username) // 클레임: 사용자 정보
			.setIssuedAt(new Date()) // 발행 시간
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
			.signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 서명 생성
			.compact();
	}

	// JWT 토큰 검증 및 클레임 파싱
	public static Claims validateToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(SECRET_KEY) // Secret Key 설정
			.build()
			.parseClaimsJws(token) // 토큰 파싱 및 검증
			.getBody();
	}
}
