package kr.co.duck.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

// 기능: 퀴즈방 Entity
@Entity
public class QuizRoom extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int quizRoomId;

	@Column(nullable = false)
	private String quizRoomName;

	@Column
	private String quizRoomPassword;

	@Column(nullable = false)
	private String owner;

	@Column(nullable = false)
	private boolean status;

	@Version // 낙관적 락을 위한 버전 필드
	@Column(name = "version")
	private int version;

	// 기본 생성자
	public QuizRoom() {
	}

	// 모든 필드를 포함하는 생성자
	public QuizRoom(String quizRoomName, String quizRoomPassword, String owner, boolean status) {
		this.quizRoomName = quizRoomName;
		this.quizRoomPassword = quizRoomPassword;
		this.owner = owner;
		this.status = status;
	}

	// Getter
	public int getQuizRoomId() {
		return quizRoomId;
	}

	public String getQuizRoomName() {
		return quizRoomName;
	}

	public String getQuizRoomPassword() {
		return quizRoomPassword;
	}

	public String getOwner() {
		return owner;
	}

	public boolean isStatus() {
		return status;
	}

	public int getVersion() {
		return version;
	}

	// Setter
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "QuizRoom{" + "quizRoomId=" + quizRoomId + ", quizRoomName='" + quizRoomName + '\''
				+ ", quizRoomPassword='" + quizRoomPassword + '\'' + ", owner='" + owner + '\'' + ", status=" + status
				+ '}';
	}
}
