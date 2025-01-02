package woodo.practice.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import woodo.practice.authservice.domain.User;

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
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
