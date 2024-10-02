package kr.co.duck.beans;

public class ContentBean {

	private int boardpost_id; //글 식별자
	private int member_id; // member_id FK
	private int board_id; // board_id FK
	private String content_title; //글 제목
	private String content_text; //글 내용(이미지 포함)
	private int like_count; //좋아요 수
	private String writedate; //글 게시일
	private String modifydate; //글 수정일	
	private String membername; //해당 글 작성자 이름
	private String board_name; //해당 게시판 이름

	public int getBoardpost_id() {
		return boardpost_id;
	}
	public void setBoardpost_id(int boardpost_id) {
		this.boardpost_id = boardpost_id;
	}
	public String getContent_title() {
		return content_title;
	}
	public void setContent_title(String content_title) {
		this.content_title = content_title;
	}
	public String getContent_text() {
		return content_text;
	}
	public void setContent_text(String content_text) {
		this.content_text = content_text;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public String getWritedate() {
		return writedate;
	}
	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}
	public String getModifydate() {
		return modifydate;
	}
	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
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

}
