package kr.co.duck.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER_GAME_STATS")
public class MemberGameStats {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stats_seq")
	@SequenceGenerator(name = "stats_seq", sequenceName = "MEMBER_GAME_STATS_SEQ", allocationSize = 1) // 시퀀스 설정
	@Column(name = "STATS_ID")
	private int statsId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID", nullable = false) // MEMBER_ID를 NULL로 설정할 수 없도록
	private Member member;

	@Column(name = "WIN_NUM", nullable = false)
	private int winNum = 0;

	@Column(name = "LOSE_NUM", nullable = false)
	private int loseNum = 0;

	@Column(name = "TOTAL_GAME_NUM", nullable = false)
	private int totalGameNum = 0;

	@Column(name = "ENTER_GAME_NUM", nullable = false)
	private int enterGameNum = 0;

	@Column(name = "SOLO_EXIT_NUM", nullable = false)
	private int soloExitNum = 0;

	@Column(name = "MAKE_ROOM_NUM", nullable = false)
	private int makeRoomNum = 0;

	@Column(name = "PLAY_TIME", nullable = false)
	private int playTime = 0;

	// 기본 생성자
	public MemberGameStats() {
	}

	// MemberGameStats 객체를 초기화하는 생성자
	public MemberGameStats(Member member) {
		this.member = member;
	}

	public int getStatsId() {
		return statsId;
	}

	public void setStatsId(int statsId) {
		this.statsId = statsId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;

		// 양방향 연관 관계 설정
		if (member != null && member.getMemberGameStats() != this) {
			member.setMemberGameStats(this);
		}
	}

	public int getWinNum() {
		return winNum;
	}

	public void setWinNum(int winNum) {
		this.winNum = winNum;
	}

	public int getLoseNum() {
		return loseNum;
	}

	public void setLoseNum(int loseNum) {
		this.loseNum = loseNum;
	}

	public int getTotalGameNum() {
		return totalGameNum;
	}

	public void setTotalGameNum(int totalGameNum) {
		this.totalGameNum = totalGameNum;
	}

	public int getEnterGameNum() {
		return enterGameNum;
	}

	public void setEnterGameNum(int enterGameNum) {
		this.enterGameNum = enterGameNum;
	}

	public int getSoloExitNum() {
		return soloExitNum;
	}

	public void setSoloExitNum(int soloExitNum) {
		this.soloExitNum = soloExitNum;
	}

	public int getMakeRoomNum() {
		return makeRoomNum;
	}

	public void setMakeRoomNum(int makeRoomNum) {
		this.makeRoomNum = makeRoomNum;
	}

	public int getPlayTime() {
		return playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	@Override
	public String toString() {
		return "MemberGameStats{" + "statsId=" + statsId + ", member="
				+ (member != null ? member.getMemberId() : "null") + ", winNum=" + winNum + ", loseNum=" + loseNum
				+ ", totalGameNum=" + totalGameNum + ", enterGameNum=" + enterGameNum + ", soloExitNum=" + soloExitNum
				+ ", makeRoomNum=" + makeRoomNum + ", playTime=" + playTime + '}';
	}
}
