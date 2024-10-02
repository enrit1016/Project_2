package kr.co.duck.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	    
	    if (quizRooms.getQuizRoomBeanList().isEmpty()) {
	        response.put("success", false);
	        response.put("message", "검색 결과가 없습니다.");
	    } else {
	        response.put("success", true);
	        response.put("data", quizRooms);
	    }
	    
	    return ResponseEntity.ok(response);
	}


	// 퀴즈방 생성 API
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createRoom(@RequestBody QuizRoomBean quizRoomBean,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Map<String, Object> response = new HashMap<>();

		try {
			// 로그인한 사용자를 가져옴
			Member member;
			if (userDetails == null) {
				// 임시로 로그인한 사용자를 가정하여 생성
				member = new Member();
				member.setMemberId(1); // 임시 ID 설정
				member.setNickname("임시사용자"); // 임시 닉네임 설정
				member.setEmail("temp@example.com"); // 임시 이메일 설정
			} else {
				member = userDetails.getMember();
			}

			QuizRoomBean createdRoom = quizRoomService.createRoom(quizRoomBean, member);

			if (createdRoom != null) {
				response.put("success", true);
				response.put("roomId", createdRoom.getQuizRoomId()); // 생성된 방의 ID 반환
			} else {
				response.put("success", false);
				response.put("message", "방 생성에 실패했습니다.");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "서버 오류가 발생했습니다.");
			e.printStackTrace(); // 서버 콘솔에 예외 로그 출력
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		return ResponseEntity.ok(response);
	}
}
