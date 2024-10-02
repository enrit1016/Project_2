package kr.co.duck.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// 기능: 퀴즈방과 유저를 연결하는 중간 Entity
@Entity
@Table(name = "quiz_room_attendee")
public class QuizRoomAttendee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quiz_room_member_id") // 칼럼명 지정
	private int quizRoomMemberId;

	@JoinColumn(name = "quiz_room_id") // 칼럼명 지정
	@OneToOne(fetch = FetchType.LAZY)
	private QuizRoom quizRoom;

	@JoinColumn(name = "member_id") // 칼럼명 지정
	@OneToOne(fetch = FetchType.LAZY)
	private Member member;

	@Column(name = "member_nickname", nullable = false) // 칼럼명 지정
	private String memberNickname;

	// 기본 생성자
	public QuizRoomAttendee() {
	}

	// 모든 필드를 포함하는 생성자
	public QuizRoomAttendee(int quizRoomMemberId, QuizRoom quizRoom, Member member, String memberNickname) {
		this.quizRoomMemberId = quizRoomMemberId;
		this.quizRoom = quizRoom;
		this.member = member;
		this.memberNickname = memberNickname;
	}

	// 게임룸과 멤버를 이용하는 생성자
	public QuizRoomAttendee(QuizRoom quizRoom, Member member) {
		this.quizRoom = quizRoom;
		this.member = member;
		this.memberNickname = member.getNickname();
	}

	// Getter 메서드
	public int getQuizRoomMemberId() {
		return quizRoomMemberId;
	}

	public QuizRoom getQuizRoom() {
		return quizRoom;
	}

	public Member getMember() {
		return member;
	}

	public String getMemberNickname() {
		return memberNickname;
	}

	@Override
	public String toString() {
		return "QuizRoomAttendee{" + "quizRoomMemberId=" + quizRoomMemberId + ", quizRoom=" + quizRoom + ", member="
				+ member + ", memberNickname='" + memberNickname + '\'' + '}';
	}
}
