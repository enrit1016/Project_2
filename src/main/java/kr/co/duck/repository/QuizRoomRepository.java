package kr.co.duck.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import kr.co.duck.domain.QuizRoom;

// 기능: 퀴즈방 레포지토리
public interface QuizRoomRepository extends JpaRepository<QuizRoom, Long> {

	// 퀴즈방 전체 조회 페이징 처리
	Page<QuizRoom> findAll(Pageable pageable);

	// 퀴즈방 페이징 처리 + 검색 기능
	Page<QuizRoom> findByQuizRoomNameContaining(Pageable pageable, String keyword);

	// 퀴즈방 단건 조회
	Optional<QuizRoom> findByQuizRoomId(Long quizRoomId);

	// 비관적 락 적용을 통한 퀴즈방 단건 조회
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select q from QuizRoom q where q.quizRoomId = :quizRoomId")
	Optional<QuizRoom> findByQuizRoomIdWithLock(Long quizRoomId);
}
