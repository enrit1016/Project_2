package kr.co.duck.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.duck.domain.Member;
import kr.co.duck.domain.QuizRoom;
import kr.co.duck.domain.QuizRoomAttendee;

// 기능: 퀴즈방에 들어간 유저 정보 레포지토리
public interface QuizRoomAttendeeRepository extends JpaRepository<QuizRoomAttendee, Long> {
	List<QuizRoomAttendee> findByQuizRoom(QuizRoom quizRoom); // 퀴즈방 객체로 안에 있는 멤버들 전부 조회

	Optional<QuizRoomAttendee> findByMember(Member member); // 멤버 객체로 참가자 정보 조회

	Optional<QuizRoomAttendee> findByMember_MemberId(int memberId); // 멤버 ID로 참가자 정보 조회

	List<QuizRoomAttendee> findByQuizRoom_QuizRoomId(int quizRoomId); // 퀴즈방 ID로 안에 있는 멤버 전부 조회

	@Transactional
	void deleteAllByMember(Member member); // 멤버 객체로 삭제

	boolean existsByMember(Member member); // 멤버 존재 여부 확인
}
