package kr.co.duck.controller;

import org.springframework.web.bind.annotation.*;
import kr.co.duck.beans.QuizBean;
import kr.co.duck.service.QuizService;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
	private final QuizService quizService;

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@PostMapping("/create")
	public String createQuiz(@RequestBody QuizBean quizBean) {
		quizService.createQuiz(quizBean);
		return "퀴즈가 생성되었습니다.";
	}

	@GetMapping("/list")
	public List<QuizBean> getAllQuizzes() {
		return quizService.getAllQuizzes();
	}

	// 퀴즈 ID로 퀴즈 가져오기
	@GetMapping("/details/{quizId}")
	public QuizBean getQuiz(@PathVariable String quizId) {
	    try {
	        int id = Integer.parseInt(quizId);
	        QuizBean quiz = quizService.getQuiz(id);
	        if (quiz != null) {
	            return quiz;
	        } else {
	            throw new IllegalArgumentException("해당 ID의 퀴즈를 찾을 수 없습니다: " + id);
	        }
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("잘못된 퀴즈 ID입니다: " + quizId);
	    }
	}


	@PutMapping("/update/{quizId}")
	public String updateQuiz(@PathVariable int quizId, @RequestBody QuizBean quizBean) {
		quizService.updateQuiz(quizId, quizBean);
		return "퀴즈가 업데이트되었습니다.";
	}

	@DeleteMapping("/delete/{quizId}")
	public String deleteQuiz(@PathVariable int quizId) {
		quizService.deleteQuiz(quizId);
		return "퀴즈가 삭제되었습니다.";
	}

	@PostMapping("/answer")
	public String submitAnswer(@RequestBody QuizBean quizBean) {
		boolean isCorrect = quizService.submitAnswer(quizBean);
		return isCorrect ? "정답입니다!" : "오답입니다!";
	}
	

}
