package kr.co.duck.beans;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class MemberBean implements Serializable {
    private static final long serialVersionUID = 1L; // 새로 추가된 부분

	private boolean memberNameExist;
	private boolean memberLogin;
	private boolean socialLogin; //소셜로그인 여부 체크
	private int member_id; // 로그인시 작성하는 id 아님 유저식별번호임

	// @Size(min = 1, max = 10)
	@Pattern(regexp = "[a-zA-Z0-9@.]*")
	private String membername; // = 아이디

	// @Size(min = 2, max = 5)
	@Pattern(regexp = "[가-힣a-zA-Z0-9]*")
	private String nickname; // 닉네임

	// @Size(min = 5, max = 20)
	@Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+=\\-`~]*")
	private String password; // 유저 비밀번호

	// @Size(min = 2, max = 20)
	@Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+=\\-`~]*")
	private String password2; // 유저 비밀번호2 (임의 추가

	// @Size(min = 1, max =3)
	//@Pattern(regexp = "[0-9]*")
	private String birthday; // 유저 나이

	@Email
	private String email; // 유저 이메일

	private String join_date;

	@Pattern(regexp = "[가-힣a-zA-Z]*")
	private String real_name;

	private String role;
	private String logintype;

	// 1 = code, 2 = input code
	private String authCode1;
	private String authCode2;
	
	private int score;

	// 기본 생성자
	public MemberBean() {
		this.memberNameExist = false;
		this.memberLogin = false;
		this.socialLogin = false;
	}
	
	
    // 새로운 생성자
    public MemberBean(int member_id, String nickname, String email) {
        this.member_id = member_id;
        this.nickname = nickname;
        this.email = email;
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

	public boolean isSocialLogin() {
		return socialLogin;
	}

	public void setSocialLogin(boolean socialLogin) {
		this.socialLogin = socialLogin;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
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

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJoin_date() {
		return join_date;
	}

	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public String getAuthCode1() {
		return authCode1;
	}

	public void setAuthCode1(String authCode1) {
		this.authCode1 = authCode1;
	}

	public String getAuthCode2() {
		return authCode2;
	}

	public void setAuthCode2(String authCode2) {
		this.authCode2 = authCode2;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	

}
