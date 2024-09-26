package kr.co.duck.beans;

import java.time.LocalDateTime;

public class ChatBean {

	private int chat_id; // 채팅창의 인덱스번호
	private int chat_user_id; // 유저 식별번호
	private String chat_text; // 채팅 내용
	private LocalDateTime chat_time; // 채팅을 적은 시간
	private int user_score; 

	public int getChat_id() {
		return chat_id;
	}

	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}

	public int getChat_user_id() {
		return chat_user_id;
	}

	public void setChat_user_id(int chat_user_id) {
		this.chat_user_id = chat_user_id;
	}

	public String getChat_text() {
		return chat_text;
	}

	public void setChat_text(String chat_text) {
		this.chat_text = chat_text;
	}

	public LocalDateTime getChat_time() {
		return chat_time;
	}

	public void setChat_time(LocalDateTime chat_time) {
		this.chat_time = chat_time;
	}

	public int getUser_score() {
		return user_score;
	}

	public void setUser_score(int user_score) {
		this.user_score = user_score;
	}

}
