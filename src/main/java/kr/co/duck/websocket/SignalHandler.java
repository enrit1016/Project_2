package kr.co.duck.websocket;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.duck.beans.WebSocketMessageBean;
import kr.co.duck.beans.WebSocketResponseBean;
import kr.co.duck.repository.SessionRepository;
import kr.co.duck.service.QuizRoomService;
import kr.co.duck.service.WebSocketService;

@Component
public class SignalHandler extends TextWebSocketHandler {

	private final QuizRoomService quizRoomService;
	private final WebSocketService webSocketService;
	private final SessionRepository sessionRepository;
	private final ObjectMapper objectMapper;

	private static final String MSG_TYPE_JOIN_ROOM = "join_room";
	private static final String MSG_TYPE_OFFER = "offer";
	private static final String MSG_TYPE_ANSWER = "answer";
	private static final String MSG_TYPE_CANDIDATE = "candidate";

	// 생성자를 통한 의존성 주입
	public SignalHandler(QuizRoomService quizRoomService, WebSocketService webSocketService,
			SessionRepository sessionRepository) {
		this.quizRoomService = quizRoomService;
		this.webSocketService = webSocketService;
		this.sessionRepository = sessionRepository;
		this.objectMapper = new ObjectMapper(); // ObjectMapper 인스턴스 생성
	}

	@Override
	public void afterConnectionEstablished(final WebSocketSession session) {
		// 웹소켓이 연결되면 실행되는 메소드
	}

	// 시그널링 처리 메소드
	@Override
	protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage) {
		try {
			WebSocketMessageBean message = objectMapper.readValue(textMessage.getPayload(), WebSocketMessageBean.class);
			String userName = message.getSender();
			Long roomId = message.getRoomId();

			switch (message.getType()) {
			case MSG_TYPE_JOIN_ROOM:
				webSocketService.handleJoinRoom(session, message, roomId, userName);
				break;

			case MSG_TYPE_OFFER:
			case MSG_TYPE_ANSWER:
			case MSG_TYPE_CANDIDATE:
				webSocketService.handleOfferAnswerCandidate(session, message, roomId);
				break;

			default:
				// 로깅 또는 기타 처리
				break;
			}
		} catch (JsonProcessingException e) {
			// 예외 처리 로깅
		}
	}

	@Override
	@Transactional
	public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
		webSocketService.handleDisconnect(session);
	}

	// 메세지 발송
	private void sendMessage(final WebSocketSession session, final WebSocketResponseBean message) {
		try {
			String json = objectMapper.writeValueAsString(message);
			session.sendMessage(new TextMessage(json));
		} catch (IOException e) {
			// 예외 처리 로깅
		}
	}
}
