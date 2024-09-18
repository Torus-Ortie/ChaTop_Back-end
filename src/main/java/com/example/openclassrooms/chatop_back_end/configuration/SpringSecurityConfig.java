package com.example.openclassrooms.chatop_back_end.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class SpringSecurityConfig {

	private String jwtKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCxZFYZG32jktM+2Afseme/0Ab4WKdrp5RXmt05rqc+O/m3VXkIOAICzpp2Nm6KIFZbAn7YQfRUOoNfScD8kZEgh1gZsu2TM+WcZNFj/k4lzZPaoEZeg8GeyZisZK6js3tL6IE7Meeo0UNgb5CFVW2WF5DYSPkkH3+cjO3sUpM3UIpGXzph+bdeDlU2zixgoyvEOKEoUcFrpZeml+mWZDlCJd/NeVXSdRB8pfCqr7orhrfaL660AFUKw3vAozOf/Nz5KqaOJUy6anEG4ouGBfqpongG9to7c+M2F6GwpaJ1o1Gdh2Umk5CkfT0hr2NeDFec8U5Y4BK0Ye0euuzY/OnBK+TCCBfSFaPkFmFqFfFn1njGqwA6DuYN49E/c83c+S3Cd5CPGzoQM+cKAr58EBUVgFUkLhnJztoGUfo/RNLYPMsdZhhwGScVA9HVpXx6EPGdYUziQuvDunhFkQLD/XBOD85pME/r31gNhHmsXkhrCVk6zWd9NPemkq/e5m1q9uTMDT4COtSzGRGNpBgCOjBEDdoPRFgcmsSsd3BWYehUSPzr6k+EPyQzH2LChMK1eUQnI++a4ckfQWhGc9u/0d6wPMIBmqPxLEE3BcTkvY4O7+3spqohxaFtS1io4Cl+UYtsV4qKL1hqAvmo9Z/eH6awSAHNsbJK4cCi2hoXKbnvAQ== torus@DESKTOP-DTEUB9G";

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
				.httpBasic(Customizer.withDefaults())
                .build();
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length, "RSA");
		return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}

	@Bean
	public UserDetailsService users() {
		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}