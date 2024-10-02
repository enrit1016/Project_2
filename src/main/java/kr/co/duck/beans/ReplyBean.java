package kr.co.duck.beans;

public class ReplyBean {

	private int reply_id; //댓글식별자
	private int boardpost_id; //boardpost_id FK
	private int member_id; //member_id FK
	private String reply_text; //댓글내용	
	private String reply_date; //댓글 쓴 날짜
	private String reply_modify; //댓글 수정 날짜
	private String reply_writer_name; //해당 댓글 글쓴이 이름

	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	public String getReply_text() {
		return reply_text;
	}
	public void setReply_text(String reply_text) {
		this.reply_text = reply_text;
	}
	public String getReply_date() {
		return reply_date;
	}
	public void setReply_date(String reply_date) {
		this.reply_date = reply_date;
	}
	public String getReply_modify() {
		return reply_modify;
	}
	public void setReply_modify(String reply_modify) {
		this.reply_modify = reply_modify;
	}
	public int getBoardpost_id() {
		return boardpost_id;
	}
	public void setBoardpost_id(int boardpost_id) {
		this.boardpost_id = boardpost_id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getReply_writer_name() {
		return reply_writer_name;
	}
	public void setReply_writer_name(String reply_writer_name) {
		this.reply_writer_name = reply_writer_name;
	}

}
