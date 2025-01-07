package woodo.practice.apigateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;
import woodo.practice.apigateway.util.JwtUtil;

@Component
public class JwtAuthenticationFilter implements WebFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		System.out.println(">>>> exchange = " + exchange);
		ServerHttpRequest request = exchange.getRequest();

		// Authorization 헤더 확인
		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return chain.filter(exchange);
		}

		String token = authHeader.substring(7);
		System.out.println(">>> token = " + token);

		if (!jwtUtil.isTokenValid(token)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		String username = jwtUtil.extractClaims(token).getSubject();

		// Security Context 설정
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);

		return chain.filter(exchange)
			.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
	}
}