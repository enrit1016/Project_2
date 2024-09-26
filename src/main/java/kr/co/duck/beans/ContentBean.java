package kr.co.duck.beans;

import org.springframework.web.multipart.MultipartFile;

public class ContentBean {

	private int content_id; //글 식별자
	private String content_title; //글 제목
	private String content_text; //글 내용
	private String image; //파일의 '이름'을 담는 변수
	private MultipartFile upload_file; //브라우저가 보낸 파일 데이터
	private int like_count; //좋아요 수
	private String write_date; //글 게시일
	private String modified_date; //글 수정일	
	private int board_id; // board_id FK
	private String board_name; //해당 게시판 이름
	private int member_id; // member_id FK
	private String membername; //해당 글 작성자 이름

	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public MultipartFile getUpload_file() {
		return upload_file;
	}
	public void setUpload_file(MultipartFile upload_file) {
		this.upload_file = upload_file;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public String getMembername() {
		return membername;
	}
	public void setMember_name(String member_name) {
		this.membername = member_name;
	}

}
