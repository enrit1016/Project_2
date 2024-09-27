package kr.co.duck.beans;

public class ReplyBean {

	private int reply_id; //댓글식별자
	private String reply_text; //댓글내용	
	private String reply_date; //댓글 쓴 날짜
	private String reply_modify_data; //댓글 수정 날짜
	private int content_id; //content_id FK
	private int member_id; //member_id FK
	private String membername; //해당 댓글 글쓴이 이름

	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	public String getReply_text() {
		return reply_text;
	}
	public void setReply_text(String rep_text) {
		this.reply_text = rep_text;
	}
	public String getReply_date() {
		return reply_date;
	}
	public void setReply_date(String rep_date) {
		this.reply_date = rep_date;
	}
	public String getReply_modify_data() {
		return reply_modify_data;
	}
	public void setReply_modify_data(String rep_modify_data) {
		this.reply_modify_data = rep_modify_data;
	}
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int rep_content_id) {
		this.content_id = rep_content_id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int rep_member_id) {
		this.member_id = rep_member_id;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}

}
