package kr.co.duck.beans;

public class QuizRoomBean {
	private int quizRoomId; // quiz_room_id와 매핑
	private String quizRoomName; // quiz_room_name과 매핑
	private String quizRoomPassword; // quiz_room_password와 매핑
	private String owner; // owner와 매핑
	private int status; // status와 매핑 (0: 닫힘, 1: 열림)
	private int memberCount; // member_count와 매핑
	private String members; // 콤마로 구분된 멤버 ID 문자열

	// 기본 생성자
	public QuizRoomBean() {
	}

	// 모든 필드를 포함하는 생성자
	public QuizRoomBean(int quizRoomId, String quizRoomName, String quizRoomPassword, String owner, int status,
			int memberCount, String members) {
		this.quizRoomId = quizRoomId;
		this.quizRoomName = quizRoomName;
		this.quizRoomPassword = quizRoomPassword;
		this.owner = owner;
		this.status = status;
		this.memberCount = memberCount;
		this.members = members;
	}

	// Getter와 Setter
	public int getQuizRoomId() {
		return quizRoomId;
	}

	public void setQuizRoomId(int quizRoomId) {
		this.quizRoomId = quizRoomId;
	}

	public String getQuizRoomName() {
		return quizRoomName;
	}

	public void setQuizRoomName(String quizRoomName) {
		this.quizRoomName = quizRoomName;
	}

	public String getQuizRoomPassword() {
		return quizRoomPassword;
	}

	public void setQuizRoomPassword(String quizRoomPassword) {
		this.quizRoomPassword = quizRoomPassword;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "QuizRoomBean{" + "quizRoomId=" + quizRoomId + ", quizRoomName='" + quizRoomName + '\''
				+ ", quizRoomPassword='" + quizRoomPassword + '\'' + ", owner='" + owner + '\'' + ", status=" + status
				+ ", memberCount=" + memberCount + ", members='" + members + '\'' + '}';
	}
}
