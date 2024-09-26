package kr.co.duck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.duck.beans.QuizBean;
import kr.co.duck.service.QuizService;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;


	// 퀴즈 목록을 보여주는 메서드
	@GetMapping("/quizMain")
	public String getQuizList(Model model) {
		List<QuizBean> quizzes = quizService.getAllQuizzes();
		System.out.println("퀴즈 목록: " + quizzes); // 퀴즈목록 출력확인
		model.addAttribute("quizzes", quizzes);
		return "quiz/quizMain"; // 퀴즈 목록 페이지
	}

	// 특정 퀴즈 시작
	@GetMapping("/{quizId}")
	public String startQuiz(@PathVariable int quizId, Model model) {
		QuizBean quiz = quizService.getQuizById(quizId);
		if (quiz == null) {
			model.addAttribute("errorMessage", "퀴즈를 찾을 수 없습니다.");
			return "error/quizNotFound"; // 에러 페이지로 이동
		}
		model.addAttribute("quiz", quiz);
		return "quiz/startQuiz"; // 퀴즈 시작 페이지
	}
}
