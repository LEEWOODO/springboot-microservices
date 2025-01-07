package woodo.practice.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.RequiredArgsConstructor;
import woodo.practice.apigateway.filter.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * @param http ServerHttpSecurity
	 * @return SecurityWebFilterChain
	 * @Description HttpSecurity 설정
	 * 						- CSRF 비활성화 : CSRF 토큰을 사용하지 않을 것이므로 확인하지 않도록 설정
	 * 						- CORS 설정 : 다른 도메인의 웹 페이지에서 리소스에 접근할 수 있도록 기본 설정 사용
	 *						- 폼 로그인과 HTTP 기본 인증을 비활성화 : Spring 웹 페이지에서 제공되는 로그인 폼을 통해 사용자를 인증하는 메커니즘과 HTTP 기반 기본 인증을 비활성화한다.
	 * @since 2025. 1. 2.
	 */
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
			.csrf().disable()
			.cors(Customizer.withDefaults())
			.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
			.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
			.authorizeExchange(exchanges -> exchanges
				.pathMatchers("/api/auth/**").permitAll() // Public endpoints
				.anyExchange().authenticated()            // Secure other endpoints
			)
			.addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) // Add JWT filter
			.build();
	}
}