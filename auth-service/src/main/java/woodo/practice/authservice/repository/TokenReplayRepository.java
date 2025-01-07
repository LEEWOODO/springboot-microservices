package woodo.practice.authservice.repository;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * Project        : springboot-microservices
 * DATE           : 2025. 1. 6.
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 1. 6.      dnejdzlr2          토큰 재사용 감지를 위한 Repository
 * 2025. 1. 6.      dnejdzlr2          로그아웃 시 사용한 토큰을 재사용하지 못하도록 처리(7일)
 * 									     - 로그아웃 시 사용한 토큰을 재사용하면 로그아웃이 무의미해짐
 * 									     - 로그아웃 시 사용한 토큰을 재사용하여 악의적인 사용자가 로그인할 수 있음
 */
@Repository
@RequiredArgsConstructor
public class TokenReplayRepository {
	private final RedisTemplate<String, String> redisTemplate;
	private static final String USED_TOKEN_PREFIX = "used_refresh_token:";

	public void markTokenAsUsed(String refreshToken) {
		String key = USED_TOKEN_PREFIX + refreshToken;
		redisTemplate.opsForValue().set(key, "used", Duration.ofDays(7));
	}

	public boolean isTokenReused(String refreshToken) {
		String key = USED_TOKEN_PREFIX + refreshToken;
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

}
