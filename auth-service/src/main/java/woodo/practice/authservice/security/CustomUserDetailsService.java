package woodo.practice.authservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import woodo.practice.authservice.repository.UserRepository;

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
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// return userRepository.findByUsername(username)
		// 	.map(user -> org.springframework.security.core.userdetails.User
		// 		.withUsername(user.getUsername())
		// 		.password(user.getPassword())
		// 		.roles(user.getRole())
		// 		.build())
		// 	.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return userRepository.findByUsername(username)
			.map(user -> org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				// roles()메소드는 자동으로 'ROLE_' 접두사를 추가합니다
				.roles(user.getRole().replace("ROLE_", ""))
				.build())
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
