package woodo.practice.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

	boolean existsByUsername(@NotBlank @Size(min = 4, max = 20) String username);

	boolean existsByEmail(@NotBlank @Email String email);
}
