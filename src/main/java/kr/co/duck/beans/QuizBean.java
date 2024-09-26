package kr.co.duck.beans;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "quiz")
public class QuizBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quiz_id")
	private int quizId; // 퀴즈 식별 아이디

	@Column(name = "quiz_title")
	private String quizTitle; // 퀴즈 제목

	@Column(name = "quiz_text")
	private String quizText; // 퀴즈 설명

	@Column(name = "quiz_question_type")
	private String quizQuestionType; // 퀴즈 유형

	@Column(name = "quiz_answer")
	private String quizAnswer; // 퀴즈 정답

	@Column(name = "quiz_score")
	private int quizScore; // 퀴즈 정답 점수

	@Column(name = "quiz_hint")
	private String quizHint; // 퀴즈 힌트

	@Column(name = "join_user")
	private int joinUser; // 첫 번째로 입장한 사람 (방장) 식별자

	@Column(name = "user_id")
	private int userId; // 유저 식별번호

	// OneToMany 관계 설정
	@OneToMany
	@JoinColumn(name = "quiz_id") // song_ids 테이블에 외래 키로 저장됩니다.
	private List<SongBean> songIds; // 퀴즈에 사용할 노래 정보 식별자 목록

	@ElementCollection
	@CollectionTable(name = "quiz_join_ids", joinColumns = @JoinColumn(name = "quiz_id"))
	@Column(name = "join_id")
	private List<Integer> quizJoinIds; // 퀴즈에 참가한 사람들의 식별자 목록

	// 기본 생성자 (필수)
	public QuizBean() {
	}

	// 생성자
	public QuizBean(int quizId, String quizTitle, String quizText, String quizQuestionType, String quizAnswer,
			int quizScore, String quizHint, int joinUser, int userId, List<SongBean> songIds,
			List<Integer> quizJoinIds) {
		this.quizId = quizId;
		this.quizTitle = quizTitle;
		this.quizText = quizText;
		this.quizQuestionType = quizQuestionType;
		this.quizAnswer = quizAnswer;
		this.quizScore = quizScore;
		this.quizHint = quizHint;
		this.joinUser = joinUser;
		this.userId = userId;
		this.songIds = songIds;
		this.quizJoinIds = quizJoinIds;
	}

	// Getter 및 Setter
	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public String getQuizTitle() {
		return quizTitle;
	}

	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}

	public String getQuizText() {
		return quizText;
	}

	public void setQuizText(String quizText) {
		this.quizText = quizText;
	}

	public String getQuizQuestionType() {
		return quizQuestionType;
	}

	public void setQuizQuestionType(String quizQuestionType) {
		this.quizQuestionType = quizQuestionType;
	}

	public String getQuizAnswer() {
		return quizAnswer;
	}

	public void setQuizAnswer(String quizAnswer) {
		this.quizAnswer = quizAnswer;
	}

	public int getQuizScore() {
		return quizScore;
	}

	public void setQuizScore(int quizScore) {
		this.quizScore = quizScore;
	}

	public String getQuizHint() {
		return quizHint;
	}

	public void setQuizHint(String quizHint) {
		this.quizHint = quizHint;
	}

	public int getJoinUser() {
		return joinUser;
	}

	public void setJoinUser(int joinUser) {
		this.joinUser = joinUser;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<SongBean> getSongIds() {
		return songIds;
	}

	public void setSongIds(List<SongBean> songIds) {
		this.songIds = songIds;
	}

	public List<Integer> getQuizJoinIds() {
		return quizJoinIds;
	}

	public void setQuizJoinIds(List<Integer> quizJoinIds) {
		this.quizJoinIds = quizJoinIds;
	}
}
