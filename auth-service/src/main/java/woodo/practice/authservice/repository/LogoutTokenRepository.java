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
 * 2025. 1. 6.      dnejdzlr2          최초 생성
 */
@Repository
@RequiredArgsConstructor
public class LogoutTokenRepository {
	private final RedisTemplate<String, String> redisTemplate;
	private static final String KEY_PREFIX = "logout_token:";

	public void saveLogoutToken(String accessToken, long expirationTime) {
		String key = KEY_PREFIX + accessToken;
		redisTemplate.opsForValue().set(key, "logout", Duration.ofMillis(expirationTime));
	}

	public boolean isLogoutToken(String accessToken) {
		String key = KEY_PREFIX + accessToken;
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}
}
