package kr.co.duck.beans;

public class ReplyBean {

	private int reply_id; //댓글식별자
	private String rep_text; //댓글내용	
	private String rep_date; //댓글 쓴 날짜
	private String rep_modify_data; //댓글 수정 날짜
	private int rep_content_id; //content_id FK
	private int rep_member_id; //member_id FK

	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	public String getRep_text() {
		return rep_text;
	}
	public void setRep_text(String rep_text) {
		this.rep_text = rep_text;
	}
	public String getRep_date() {
		return rep_date;
	}
	public void setRep_date(String rep_date) {
		this.rep_date = rep_date;
	}
	public String getRep_modify_data() {
		return rep_modify_data;
	}
	public void setRep_modify_data(String rep_modify_data) {
		this.rep_modify_data = rep_modify_data;
	}
	public int getRep_content_id() {
		return rep_content_id;
	}
	public void setRep_content_id(int rep_content_id) {
		this.rep_content_id = rep_content_id;
	}
	public int getRep_member_id() {
		return rep_member_id;
	}
	public void setRep_member_id(int rep_member_id) {
		this.rep_member_id = rep_member_id;
	}

}
