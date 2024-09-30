package kr.co.duck.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// 세션 관리 설정: SessionCreationPolicy를 통해 직접 구성
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic().disable() // httpBasic
																													// 설정
																													// 비활성화
				.authorizeRequests().antMatchers("/auth/**").permitAll().antMatchers(HttpMethod.GET, "/posts/myPost")
				.authenticated().antMatchers(HttpMethod.GET, "/**").permitAll().antMatchers("/ws-stomp").permitAll()
				.antMatchers("/signal/**").permitAll().antMatchers("/signal").permitAll().anyRequest().authenticated()
				.and().cors().configurationSource(corsConfigurationSource()); // CORS 설정을 명시적으로 지정

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:8080");
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowedOriginPatterns(Collections.singletonList("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
}
