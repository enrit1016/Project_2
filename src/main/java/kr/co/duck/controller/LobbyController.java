package kr.co.duck.controller;

import kr.co.duck.beans.QuizRoomListBean;
import kr.co.duck.service.QuizRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quiz")
public class LobbyController {

	private final QuizRoomService quizRoomService;

	@Autowired
	public LobbyController(QuizRoomService quizRoomService) {
		this.quizRoomService = quizRoomService;
	}

	// 로비 페이지로 이동
	@GetMapping("/quizlobby")
	public String showLobby(Model model) {
		// 퀴즈방 목록을 가져와 모델에 추가
		QuizRoomListBean quizRooms = quizRoomService.getAllQuizRooms(null);
		model.addAttribute("rooms", quizRooms.getQuizRoomBeanList());

		return "quiz/quizlobby"; // 
	}
}
