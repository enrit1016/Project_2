package kr.co.duck.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.duck.beans.QuizRoomBean;
import kr.co.duck.beans.QuizRoomListBean;
import kr.co.duck.domain.Member;
import kr.co.duck.service.QuizRoomService;
import kr.co.duck.util.CustomException;
import kr.co.duck.util.StatusCode;
import kr.co.duck.util.UserDetailsImpl;

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
	public ResponseEntity<Map<String, Object>> searchQuizRooms(Pageable pageable,
			@RequestParam(required = false) String keyword) {
		Map<String, Object> response = new HashMap<>();
		if (keyword == null || keyword.isEmpty()) {
			response.put("success", false);
			response.put("message", "검색 키워드가 필요합니다.");
			return ResponseEntity.badRequest().body(response);
		}

		QuizRoomListBean quizRooms = quizRoomService.searchQuizRoom(pageable, keyword);
		response.put("success", !quizRooms.getQuizRoomBeanList().isEmpty());
		response.put("data", quizRooms);
		response.put("message", quizRooms.getQuizRoomBeanList().isEmpty() ? "검색 결과가 없습니다." : "검색에 성공했습니다.");

		return ResponseEntity.ok(response);
	}

	// 퀴즈방 상세 조회 API
	@GetMapping("/{roomId}")
	public ResponseEntity<Map<String, Object>> getQuizRoom(@PathVariable int roomId) {
		Map<String, Object> response = new HashMap<>();
		try {
			QuizRoomBean quizRoom = quizRoomService.findRoomById(roomId);
			response.put("success", true);
			response.put("room", quizRoom);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "방 조회 중 오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 퀴즈방 생성 API
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createRoom(@RequestBody QuizRoomBean quizRoomBean,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Map<String, Object> response = new HashMap<>();
		try {
			Member member = getMember(userDetails);
			QuizRoomBean createdRoom = quizRoomService.createRoom(quizRoomBean, member);

			response.put("success", true);
			response.put("roomId", createdRoom.getQuizRoomId()); // 생성된 방의 ID 반환
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "방 생성에 실패했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 방 참여 API
	@PostMapping("/join")
	public ResponseEntity<Map<String, Object>> joinRoom(@RequestParam int roomId,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Map<String, Object> response = new HashMap<>();
		try {
			// userDetails가 null인 경우 처리
			if (userDetails == null) {
				throw new CustomException(StatusCode.INVALID_TOKEN, "로그인 후 사용해 주세요.");
			}

			quizRoomService.enterQuizRoom(roomId, userDetails); // UserDetailsImpl 전달

			// 참여에 성공한 경우 roomId를 응답에 포함
			response.put("success", true);
			response.put("roomId", roomId);
			return ResponseEntity.ok(response);
		} catch (CustomException e) {
			response.put("success", false);
			response.put("message", "방 참여에 실패했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "서버 오류가 발생했습니다: " + e.getMessage());
			e.printStackTrace(); // 서버 로그에 예외 출력
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 로그인한 사용자를 가져오는 헬퍼 메소드
	private Member getMember(UserDetailsImpl userDetails) {
		if (userDetails == null) {
			// 임시 사용자 정보 생성
			return new Member(1, "임시사용자", "temp@example.com");
		}
		return userDetails.getMember();
	}
}
