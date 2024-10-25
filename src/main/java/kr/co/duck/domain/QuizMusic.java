package kr.co.duck.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import kr.co.duck.util.ListToJsonConverter;

@Entity
@Table(name = "quiz_music") // quiz_music 테이블과 매핑
@IdClass(QuizMusicId.class) // 복합 키 설정 (music_id)
public class QuizMusic {

	@Id
	@Column(name = "quiz_id") // 음악 ID
	private int quizId;

	@Id
	@Column(name = "music_id") // 음악 ID
	private int musicId;

	@Column(name = "music_name", length = 255) // 음악 제목
	private String name;

	@Column(name = "video_code", length = 255) // YouTube 비디오 코드
	private String code;

	@Column(name = "start_time") // 시작 시간 (초 단위)
	private Integer start;

	@Convert(converter = ListToJsonConverter.class)
	@Column(name = "tags", length = 255) // 태그 (JSON 형태로 저장)
	private List<String> tags;

	@Convert(converter = ListToJsonConverter.class)
	@Column(name = "answer", length = 255) // 정답 목록 (JSON 형태로 저장)
	private List<String> answer;

	// 기본 생성자
	public QuizMusic() {
	}

	public QuizMusic(int quizId, int musicId, String name, String code, Integer start, List<String> tags,
			List<String> answer) {
		super();
		this.quizId = quizId;
		this.musicId = musicId;
		this.name = name;
		this.code = code;
		this.start = start;
		this.tags = tags;
		this.answer = answer;
	}

	// Getters and Setters
	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getMusicId() {
		return musicId;
	}

	public void setMusicId(int musicId) {
		this.musicId = musicId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "QuizMusic [quizId=" + quizId + ", musicId=" + musicId + ", name=" + name + ", code=" + code + ", start="
				+ start + ", tags=" + tags + ", answer=" + answer + "]";
	}

}
