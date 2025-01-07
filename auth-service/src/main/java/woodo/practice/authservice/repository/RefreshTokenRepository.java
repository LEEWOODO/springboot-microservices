package woodo.practice.authservice.repository;

import java.time.Duration;
import java.util.Optional;

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
public class RefreshTokenRepository {
	private final RedisTemplate<String, String> redisTemplate;
	private static final String KEY_PREFIX = "refresh-token:";

	public void save(String username, String refreshToken, long expirationTime) {
		String key = KEY_PREFIX + username;
		redisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(expirationTime));
	}

	public Optional<String> findByUsername(String username) {
		String key = KEY_PREFIX + username;
		String value = redisTemplate.opsForValue().get(key);
		return Optional.ofNullable(value);
	}

	public void deleteByUsername(String username) {
		String key = KEY_PREFIX + username;
		redisTemplate.delete(key);
	}
}
