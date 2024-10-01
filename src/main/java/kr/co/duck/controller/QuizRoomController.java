package kr.co.duck.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.duck.beans.QuizRoomBean;
import kr.co.duck.beans.QuizRoomListBean;
import kr.co.duck.domain.Member;
import kr.co.duck.service.QuizRoomService;

@RestController
@RequestMapping("/quiz/rooms")
public class QuizRoomController {

	private final QuizRoomService quizRoomService;

	@Autowired
	public QuizRoomController(QuizRoomService quizRoomService) {
		this.quizRoomService = quizRoomService;
	}

	// 퀴즈방 전체 조회 API
	@GetMapping
	public ResponseEntity<QuizRoomListBean> getAllQuizRooms(Pageable pageable) {
		QuizRoomListBean quizRooms = quizRoomService.getAllQuizRooms(pageable);
		return ResponseEntity.ok(quizRooms);
	}

	// 퀴즈방 키워드 검색 API
	@GetMapping("/search")
	public ResponseEntity<QuizRoomListBean> searchQuizRooms(Pageable pageable,
			@RequestParam(required = false) String keyword) {
		if (keyword == null || keyword.isEmpty()) {
			return ResponseEntity.badRequest().body(new QuizRoomListBean()); // 빈 결과 반환 또는 적절한 메시지
		}
		QuizRoomListBean quizRooms = quizRoomService.searchQuizRoom(pageable, keyword);
		return ResponseEntity.ok(quizRooms);
	}

	// 퀴즈방 생성 API
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createRoom(@RequestBody QuizRoomBean quizRoomBean) {
		Map<String, Object> response = new HashMap<>();

		// 여기서 현재 로그인한 사용자를 가져와야 합니다. 예: Member member = userService.getCurrentUser();
		// 지금은 간단하게 null로 처리합니다.
		Member member = null;

		QuizRoomBean createdRoom = quizRoomService.createRoom(quizRoomBean);

		if (createdRoom != null) {
			response.put("success", true);
			response.put("roomId", createdRoom.getQuizRoomId()); // 생성된 방의 ID 반환
		} else {
			response.put("success", false);
		}
		return ResponseEntity.ok(response);
	}
}
