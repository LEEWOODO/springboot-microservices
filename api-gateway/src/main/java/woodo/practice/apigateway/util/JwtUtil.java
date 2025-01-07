package woodo.practice.apigateway.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * Project        : springboot-microservices
 * DATE           : 2025. 1. 3.
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 1. 3.      dnejdzlr2          최초 생성
 */

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	private SecretKey key;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException {
		byte[] keyBytes = MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8));
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public Claims extractClaims(String token) {
		// key와 token 검증
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			// log.info("Invalid JWT Token", e);
			System.out.println("Invalid JWT Token");
		} catch (ExpiredJwtException e) {
			// log.info("Expired JWT Token", e);
			System.out.println("Expired JWT Token");
		} catch (UnsupportedJwtException e) {
			// log.info("Unsupported JWT Token", e);
			System.out.println("Unsupported JWT Token");
		} catch (IllegalArgumentException e) {
			// log.info("JWT claims string is empty.", e);
			System.out.println("JWT claims string is empty.");
		}
		return false;
	}
}