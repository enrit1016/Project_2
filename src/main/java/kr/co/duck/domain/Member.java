package kr.co.duck.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "email", nullable = false, unique = true)
	@Email
	private String email;

	@Column(name = "nickname", nullable = false, unique = true)
	@Size(min = 2, max = 5)
	@Pattern(regexp = "[가-힣a-zA-Z0-9]*")
	private String nickname;

	@Column(name = "password", nullable = false)
	@Size(min = 5, max = 20)
	@Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+=\\-`~]*")
	private String password;

	@Column(name = "kakao_id")
	private int kakaoId;

	@Column(name = "win_num", nullable = false)
	private int winNum = 0;

	@Column(name = "lose_num", nullable = false)
	private int loseNum = 0;

	@Column(name = "total_game_num", nullable = false)
	private int totalGameNum = 0;

	@Column(name = "enter_game_num", nullable = false)
	private int enterGameNum = 0;

	@Column(name = "solo_exit_num", nullable = false)
	private int soloExitNum = 0;

	@Column(name = "make_room_num", nullable = false)
	private int makeRoomNum = 0;

	@Column(name = "play_time", nullable = false)
	private int playTime = 0;

	@Column(name = "member_name_exist")
	private boolean memberNameExist = false;

	@Column(name = "member_login")
	private boolean memberLogin = false;

	// 기본 생성자
	public Member() {
		this.memberNameExist = false;
		this.memberLogin = false;
	}

	// 이메일, 닉네임, 패스워드를 이용한 생성자
	public Member(String email, String nickname, String password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	// Getter 및 Setter 메서드
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getKakaoId() {
		return kakaoId;
	}

	public void setKakaoId(int kakaoId) {
		this.kakaoId = kakaoId;
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

	public boolean isMemberNameExist() {
		return memberNameExist;
	}

	public void setMemberNameExist(boolean memberNameExist) {
		this.memberNameExist = memberNameExist;
	}

	public boolean isMemberLogin() {
		return memberLogin;
	}

	public void setMemberLogin(boolean memberLogin) {
		this.memberLogin = memberLogin;
	}

	// 업데이트 메서드
	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateWinNum(int num) {
		this.winNum += num;
	}

	public void updateLoseNum(int num) {
		this.loseNum += num;
	}

	public void updateTotalGame(int num) {
		this.totalGameNum += num;
	}

	public void updateSoloExit(int l) {
		this.soloExitNum += l;
	}

	public void updateMakeRoom(int num) {
		this.makeRoomNum += num;
	}

	public void updateEnterGame(int num) {
		this.enterGameNum += num;
	}

	public void updatePlayTime(int num) {
		this.playTime += num;
	}

	@Override
	public String toString() {
		return "Member{" + "id=" + id + ", email='" + email + '\'' + ", nickname='" + nickname + '\'' + ", password='"
				+ password + '\'' + ", kakaoId=" + kakaoId + ", winNum=" + winNum + ", loseNum=" + loseNum
				+ ", totalGameNum=" + totalGameNum + ", enterGameNum=" + enterGameNum + ", soloExitNum=" + soloExitNum
				+ ", makeRoomNum=" + makeRoomNum + ", playTime=" + playTime + ", memberNameExist=" + memberNameExist
				+ ", memberLogin=" + memberLogin + '}';
	}
}
