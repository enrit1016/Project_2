package kr.co.duck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

// 기능: Spring Security 사용에 필요한 설정
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// 세션 관리 설정
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// HTTP 기본 설정
		http.httpBasic().disable().authorizeRequests().antMatchers("/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/posts/myPost").authenticated().antMatchers(HttpMethod.GET, "/**")
				.permitAll().antMatchers("/ws-stomp").permitAll().antMatchers("/signal/**").permitAll()
				.antMatchers("/signal").permitAll().anyRequest().authenticated().and().cors();

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:8080"); // 로컬 개발 환경
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowedOriginPatterns(Collections.singletonList("*")); // 모든 출처 허용
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
}
