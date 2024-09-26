package kr.co.duck.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.duck.beans.QuizBean;
import kr.co.duck.mapper.QuizMapper;

@Repository
public class QuizDao {

	@Autowired
	private QuizMapper quizMapper;

	// 모든 퀴즈를 가져오는 메서드
	public List<QuizBean> getAllQuizzes() {
		return quizMapper.getAllQuizzes();
	}

	// ID로 퀴즈 조회
	public QuizBean getQuizById(int quizId) {
		return quizMapper.getQuizById(quizId);
	}

	// 새로운 퀴즈 추가
	public void addQuiz(QuizBean quiz) {
		quizMapper.addQuiz(quiz);
	}

	// 퀴즈 수정
	public void updateQuiz(QuizBean quiz) {
		quizMapper.updateQuiz(quiz);
	}

	// 퀴즈 삭제
	public void deleteQuiz(int quizId) {
		quizMapper.deleteQuiz(quizId);
	}
}
