package kr.co.duck.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class QuizQuery {

	@PersistenceContext
	private EntityManager entityManager;

	// 퀴즈 방 전체 조회 (페이징 처리)
	public Page<QuizRoom> findQuizRoomByPageable(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10); // 기본값 설정
		}

		String query = "SELECT q FROM QuizRoom q ORDER BY q.quizRoomId DESC";
		List<QuizRoom> quizRooms = entityManager.createQuery(query, QuizRoom.class)
				.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

		int totalRows = ((Number) entityManager.createQuery("SELECT COUNT(q) FROM QuizRoom q").getSingleResult())
				.intValue();

		return new PageImpl<>(quizRooms, pageable, totalRows);
	}

	// 퀴즈 방 ID로 조회
	public QuizRoom findQuizRoomByRoomId(int roomId) {
		return entityManager.find(QuizRoom.class, roomId);
	}

	// 퀴즈 방 ID로 조회 (잠금 처리)
	@Transactional
	public QuizRoom findQuizRoomByRoomIdLock(int roomId) {
		String query = "SELECT q FROM QuizRoom q WHERE q.quizRoomId = :roomId";
		return entityManager.createQuery(query, QuizRoom.class).setParameter("roomId", roomId)
				.setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();
	}

	// 퀴즈 방 내 참가자 조회
	public List<QuizRoomAttendee> findAttendeeByQuizRoom(QuizRoom quizRoom) {
		String query = "SELECT a FROM QuizRoomAttendee a WHERE a.quizRoom = :quizRoom";
		return entityManager.createQuery(query, QuizRoomAttendee.class).setParameter("quizRoom", quizRoom)
				.getResultList();
	}

	// 퀴즈 방 내 참가자 정보 조회 (방 ID로)
	public List<QuizRoomAttendee> findAttendeeByRoomId(int roomId) {
		String query = "SELECT a FROM QuizRoomAttendee a WHERE a.quizRoom.quizRoomId = :roomId";
		return entityManager.createQuery(query, QuizRoomAttendee.class).setParameter("roomId", roomId).getResultList();
	}

	// 특정 멤버에 대한 퀴즈 방 참석자 조회
	public QuizRoomAttendee findAttendeeByMember(Member member) {
		String query = "SELECT a FROM QuizRoomAttendee a WHERE a.member = :member";
		return entityManager.createQuery(query, QuizRoomAttendee.class).setParameter("member", member)
				.getSingleResult();
	}

	// 퀴즈 방 키워드 검색
	public Page<QuizRoom> findQuizRoomByContainingKeyword(Pageable pageable, String keyword) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10); // 기본값 설정
		}

		String query = "SELECT q FROM QuizRoom q WHERE q.quizRoomName LIKE :keyword ORDER BY q.quizRoomId DESC";
		List<QuizRoom> quizRooms = entityManager.createQuery(query, QuizRoom.class)
				.setParameter("keyword", "%" + keyword + "%").setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();

		int totalRows = ((Number) entityManager
				.createQuery("SELECT COUNT(q) FROM QuizRoom q WHERE q.quizRoomName LIKE :keyword")
				.setParameter("keyword", "%" + keyword + "%").getSingleResult()).intValue();

		return new PageImpl<>(quizRooms, pageable, totalRows);
	}
}
