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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import woodo.practice.authservice.domain.User;

@Component
@Slf4j
public class JwtTokenProvider {
	private final SecretKey key;
	private final long accessTokenExpiration;
	private final long refreshTokenExpiration;

	public JwtTokenProvider(
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.access-expiration}") long accessTokenExpiration,
		@Value("${jwt.refresh-expiration}") long refreshTokenExpiration
	) throws NoSuchAlgorithmException {
		byte[] keyBytes = MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8));
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	public String generateToken(User user) {
		return buildToken(user, accessTokenExpiration);
	}

	public String generateRefreshToken(User user) {
		return buildToken(user, refreshTokenExpiration);
	}

	private String buildToken(User user, long expiration) {
		Claims claims = Jwts.claims();
		claims.put("email", user.getEmail());
		claims.put("role", user.getRole());

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(expiration);

		return Jwts.builder()
			.setSubject(user.getUsername())
			.setClaims(claims)
			.setIssuedAt(Date.from(now.toInstant()))
			.setExpiration(Date.from(tokenValidity.toInstant()))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public long getRefreshTokenExpiration() {
		return refreshTokenExpiration;
	}

	public String validateRefreshToken(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		} catch (ExpiredJwtException e) {
			throw new IllegalArgumentException("Expired refresh token");
		} catch (JwtException e) {
			throw new IllegalArgumentException("Invalid refresh token");
		}
	}

	public String getUsernameFromToken(String token) {
		return parseClaims(token).getSubject();
	}

	public long getRemainingTime(String token) {
		Date expiration = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getExpiration();

		return expiration.getTime() - System.currentTimeMillis();
	}

	public String getEmail(String token) {
		return parseClaims(token).get("email", String.class);
	}

	public String getRole(String token) {
		return parseClaims(token).get("role", String.class);
	}

	public Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}