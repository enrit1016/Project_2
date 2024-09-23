package kr.co.duck.beans;

import javax.validation.constraints.Email;  
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberBean {

	private int member_idx;
	
	private boolean memberIdExist;
	private boolean memberLogin;	
	
	public MemberBean() {
		this.memberIdExist = false;
		this.memberLogin = false;
	}
	
	@Size(min = 2, max = 10)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private int member_id; //로그인시 작성하는 id
	
	@Size(min = 2, max = 5)
	@Pattern(regexp = "[가-힣a-zA-Z0-9]*")
	private String membername;	// 이름
	
	@Size(min = 2, max = 20)
	@Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+=\\-`~]*")
	private String password;	//유저 비밀번호
	
	@Size(min = 2, max = 20)
	@Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+=\\-`~]*")
	private String password2;	//유저 비밀번호2 (임의 추가
	
	@Pattern(regexp = "[0-9]{1,3}")
	private String age;	//유저 나이
	
	@Email
	private String email;	//유저 이메일
	
	private String song_quantity;	
	private String board_quantity; 
	private String join_date; 
	private String provider;
	private String provider_id;
	
	@Pattern(regexp = "[가-힣a-zA-Z]*")
	private String real_name; 
	
	private String role;
	
	
	public int getMember_idx() {
		return member_idx;
	}
	public void setMember_idx(int member_idx) {
		this.member_idx = member_idx;
	}
	public boolean isMemberIdExist() {
		return memberIdExist;
	}
	public void setMemberIdExist(boolean memberIdExist) {
		this.memberIdExist = memberIdExist;
	}
	public boolean isMemberLogin() {
		return memberLogin;
	}
	public void setMemberLogin(boolean memberLogin) {
		this.memberLogin = memberLogin;
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSong_quantity() {
		return song_quantity;
	}
	public void setSong_quantity(String song_quantity) {
		this.song_quantity = song_quantity;
	}
	public String getBoard_quantity() {
		return board_quantity;
	}
	public void setBoard_quantity(String board_quantity) {
		this.board_quantity = board_quantity;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getProvider_id() {
		return provider_id;
	}
	public void setProvider_id(String provider_id) {
		this.provider_id = provider_id;
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
	
	
	
}
