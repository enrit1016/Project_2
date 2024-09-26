package kr.co.duck.beans;

import java.util.List;

public class QuizBean {

	private int quiz_id; // 퀴즈 장르?유형?의 식별아이디
	private String quiz_title; // 퀴즈 제목
	private String quiz_text; // 퀴즈에 대한 설명?
	private String quiz_question_type; // 퀴즈 유형(노래 가사맞추기)
	private String quiz_answer; // 퀴즈 정답, 정답에 대한 내용
	private List<SongBean> song_id; // 퀴즈에 사용할 노래정보
	private List<Integer> quiz_join_id; // 퀴즈에 참가한 사람
	private int quiz_score; // 퀴즈 정답 점수
	private String quiz_hint; // 퀴즈 힌트
	private int join_user; // 첫번재 들어온 사람이 host
	private int user_id; // 유저 식별번호

	public int getQuiz_id() {
		return quiz_id;
	}

	public void setQuiz_id(int quiz_id) {
		this.quiz_id = quiz_id;
	}

	public String getQuiz_title() {
		return quiz_title;
	}

	public void setQuiz_title(String quiz_title) {
		this.quiz_title = quiz_title;
	}

	public String getQuiz_text() {
		return quiz_text;
	}

	public void setQuiz_text(String quiz_text) {
		this.quiz_text = quiz_text;
	}

	public String getQuiz_question_type() {
		return quiz_question_type;
	}

	public void setQuiz_question_type(String quiz_question_type) {
		this.quiz_question_type = quiz_question_type;
	}

	public String getQuiz_answer() {
		return quiz_answer;
	}

	public void setQuiz_answer(String quiz_answer) {
		this.quiz_answer = quiz_answer;
	}

	public List<SongBean> getSong_id() {
		return song_id;
	}

	public void setSong_id(List<SongBean> song_id) {
		this.song_id = song_id;
	}

	public List<Integer> getQuiz_join_id() {
		return quiz_join_id;
	}

	public void setQuiz_join_id(List<Integer> quiz_join_id) {
		this.quiz_join_id = quiz_join_id;
	}

	public int getQuiz_score() {
		return quiz_score;
	}

	public void setQuiz_score(int quiz_score) {
		this.quiz_score = quiz_score;
	}

	public String getQuiz_hint() {
		return quiz_hint;
	}

	public void setQuiz_hint(String quiz_hint) {
		this.quiz_hint = quiz_hint;
	}

	public int getJoin_user() {
		return join_user;
	}

	public void setJoin_user(int join_user) {
		this.join_user = join_user;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "QuizBean [quiz_id=" + quiz_id + ", quiz_title=" + quiz_title + ", quiz_text=" + quiz_text + "]";
	}

}