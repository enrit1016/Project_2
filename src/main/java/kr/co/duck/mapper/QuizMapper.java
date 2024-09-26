package kr.co.duck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.duck.beans.QuizBean;

public interface QuizMapper {

	// 모든 퀴즈를 조회
	@Select("SELECT * FROM quizzes")
	List<QuizBean> getAllQuizzes();

	// ID로 특정 퀴즈 조회
	@Select("SELECT * FROM quizzes WHERE quiz_id = #{quizId}")
	QuizBean getQuizById(int quizId);

	// 새로운 퀴즈 추가
	@Insert("INSERT INTO quizzes(quiz_title, quiz_text, quiz_answer, quiz_question_type, quiz_score, quiz_hint) "
			+ "VALUES(#{quiz_title}, #{quiz_text}, #{quiz_answer}, #{quiz_question_type}, #{quiz_score}, #{quiz_hint})")
	@Options(useGeneratedKeys = true, keyProperty = "quiz_id") // 자동 생성된 ID를 quiz_id에 세팅
	void addQuiz(QuizBean quiz);

	// 퀴즈 수정
	@Update("UPDATE quizzes SET quiz_title = #{quiz_title}, quiz_text = #{quiz_text}, "
			+ "quiz_answer = #{quiz_answer}, quiz_question_type = #{quiz_question_type}, "
			+ "quiz_score = #{quiz_score}, quiz_hint = #{quiz_hint} WHERE quiz_id = #{quiz_id}")
	void updateQuiz(QuizBean quiz);

	// 퀴즈 삭제
	@Delete("DELETE FROM quizzes WHERE quiz_id = #{quizId}")
	void deleteQuiz(int quizId);
}
