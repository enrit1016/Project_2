package kr.co.duck.domain; // 프로젝트의 패키지 경로에 맞게 수정

import com.fasterxml.jackson.annotation.JsonInclude;

// 기능 : 프론트에서 들어오는 WebSocket 메시지와 서버로부터 프론트로 전달하는 WebSocket 메시지 DTO
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessage<T> {

	public enum MessageType {
		TALK, ENTER, OFFER, ICE, ANSWER, CAMERAOFF
	}

	private String type; // 메시지 타입
	private String roomId; // 방번호
	private String sender; // 메시지 보낸 사람
	private String message; // 메시지
	private String nickname; // 닉네임

	// 시그널링 타입
	private String offer;
	private String ice;
	private String candidate;
	private String answer;

	// 생성자
	public ChatMessage(String type, String roomId, String sender, String message, String nickname, String offer,
			String ice, String candidate, String answer) {
		this.type = type;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
		this.nickname = nickname;
		this.offer = offer;
		this.ice = ice;
		this.candidate = candidate;
		this.answer = answer;
	}

	// 기본 생성자
	public ChatMessage() {
	}

	// Getter와 Setter 메서드
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public String getIce() {
		return ice;
	}

	public void setIce(String ice) {
		this.ice = ice;
	}

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
