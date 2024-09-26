package kr.co.duck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

import kr.co.duck.websocket.SignalHandler;

// 기능: 웹소켓 사용에 필요한 설정
@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {

	private final SignalHandler signalHandler;

	// 생성자를 통한 의존성 주입
	public WebSocketConfig(SignalHandler signalHandler) {
		this.signalHandler = signalHandler;
	}

	// 웹 소켓 연결을 위한 엔드포인트 설정 및 STOMP sub/pub 엔드포인트 설정
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// STOMP 접속 주소 URL => /ws-stomp
		registry.addEndpoint("/ws-stomp") // 연결될 Endpoint
				.setAllowedOriginPatterns("*") // CORS 설정
				.withSockJS() // SockJS 설정
				.setHeartbeatTime(1000); // 연결 상태 확인 주기
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/sub"); // 구독한 것에 대한 경로
		config.setApplicationDestinationPrefixes("/pub");
	}

	// 웹 소켓 버퍼 사이즈 증축
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration.setMessageSizeLimit(160 * 64 * 1024); // default: 64 * 1024
		registration.setSendTimeLimit(100 * 10000); // default: 10 * 10000
		registration.setSendBufferSizeLimit(3 * 512 * 1024); // default: 512 * 1024
	}

	@Bean
	public WebSocketHandler signalHandler() {
		return signalHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(signalHandler(), "/signal").setAllowedOrigins("*")
				.setAllowedOrigins("http://localhost:3000", "https://namoldak.com").withSockJS(); // allow all origins
	}
}
