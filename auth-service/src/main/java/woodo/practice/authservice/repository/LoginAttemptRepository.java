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
 * 2025. 1. 6.      dnejdzlr2		로그인 시도 실패 횟수를 추적하기 위한 Repository
 * 2025. 1. 6.      dnejdzlr2		5회 이상 로그인 시도 실패 시 계정 30분 잠금
 */
@Repository
@RequiredArgsConstructor
public class LoginAttemptRepository {
	private final RedisTemplate<String, String> redisTemplate;
	private static final String ATTEMPT_PREFIX = "login_attempt:";
	private static final String LOCK_PREFIX = "account_lock:";
	private static final int MAX_ATTEMPTS = 5;
	private static final long LOCK_DURATION = Duration.ofMinutes(30).toMillis();

	public void recordFailedAttempt(String username) {
		String attemptKey = ATTEMPT_PREFIX + username;
		String currentAttempts = redisTemplate.opsForValue().get(attemptKey);
		int attempts = currentAttempts == null ? 1 : Integer.parseInt(currentAttempts) + 1;

		if (attempts >= MAX_ATTEMPTS) {
			lockAccount(username);
		} else {
			redisTemplate.opsForValue().set(attemptKey, String.valueOf(attempts), Duration.ofHours(24));
		}
	}

	public void lockAccount(String username) {
		String lockKey = LOCK_PREFIX + username;
		redisTemplate.opsForValue().set(lockKey, "locked", Duration.ofMillis(LOCK_DURATION));
		redisTemplate.delete(ATTEMPT_PREFIX + username);
	}

	public boolean isAccountLocked(String username) {
		String lockKey = LOCK_PREFIX + username;
		return Boolean.TRUE.equals(redisTemplate.hasKey(lockKey));
	}

	public void resetAttempts(String username) {
		redisTemplate.delete(ATTEMPT_PREFIX + username);
	}
}
