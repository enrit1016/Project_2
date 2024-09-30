package kr.co.duck.beans;

import java.util.List;

public class QuizRoomListBean {
	private int totalPage;
	private List<QuizRoomBean> quizRoomBeanList;

	// 기본 생성자
	public QuizRoomListBean() {
	}

	// 모든 필드를 포함하는 생성자
	public QuizRoomListBean(int totalPage, List<QuizRoomBean> quizRoomBeanList) {
		this.totalPage = totalPage;
		this.quizRoomBeanList = quizRoomBeanList;
	}

	// Getter와 Setter
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<QuizRoomBean> getQuizRoomBeanList() {
		return quizRoomBeanList;
	}

	public void setQuizRoomBeanList(List<QuizRoomBean> quizRoomBeanList) {
		this.quizRoomBeanList = quizRoomBeanList;
	}

	@Override
	public String toString() {
		return "QuizRoomListBean [totalPage=" + totalPage + ", quizRoomBeanList=" + quizRoomBeanList + "]";
	}
}
