package woodo.practice.authservice.domain;

import lombok.Getter;

@Getter
public enum RoleType {
	ADMIN("ADMIN"),
	USER("USER");

	private final String role;

	RoleType(String user) {
		this.role = user;
	}
}
