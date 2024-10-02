package kr.co.duck.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private int memberId;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "NICKNAME")
	private String nickname;

	// 게임 통계 정보와의 일대일 관계 설정
	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private MemberGameStats memberGameStats;

	// 기본 생성자 (JPA를 위해 필요)
	public Member() {
	}

	// 임시 객체 생성을 위한 생성자
	public Member(int memberId, String nickname, String email) {
		this.memberId = memberId;
		this.nickname = nickname;
		this.email = email;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public MemberGameStats getMemberGameStats() {
		return memberGameStats;
	}

	public void setMemberGameStats(MemberGameStats memberGameStats) {
		// 기존 관계를 정리
		if (this.memberGameStats != null) {
			this.memberGameStats.setMember(null);
		}

		this.memberGameStats = memberGameStats;

		// 양방향 관계 설정
		if (memberGameStats != null) {
			memberGameStats.setMember(this);
		}
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", email=" + email + ", password=" + password + ", nickname=" + nickname
				+ ", memberGameStats=" + memberGameStats + "]";
	}
}
