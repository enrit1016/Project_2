package kr.co.duck.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.duck.domain.ChatMessage;
import kr.co.duck.service.ChatService;


@RestController
public class ChatController {

	private static final Logger log = LoggerFactory.getLogger(ChatController.class);
	private final ChatService chatService;

	// 생성자
	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	// STOMP 채팅용 요청 URL
	@MessageMapping("/chat/message")
	public void message(ChatMessage message) {
		log.info("Received chat message: {}", message);
		chatService.message(message); // 오타 수정
	}

	@MessageMapping("/chat/camera")
	public void cameraControl(ChatMessage message) {
		log.info("Received camera control message: {}", message);
		chatService.cameraControl(message);
	}
}
