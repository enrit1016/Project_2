package kr.co.duck.beans;

public class BoardBean {

	private int board_id; //게시판 식별자
	private String board_name; //0:자유 1:소식/정보 2:음악 추천

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

}
