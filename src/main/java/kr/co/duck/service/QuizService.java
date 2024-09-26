package kr.co.duck.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.duck.beans.QuizBean;

@Service
public class QuizService {
    private List<QuizBean> quizzes = new ArrayList<>();

    public QuizService() {
    	System.out.println("퀴즈 서비스 초기화");
        // 예시 퀴즈 추가
        addExampleQuizzes();
    }

    // 예시 퀴즈를 추가하는 메서드
    private void addExampleQuizzes() {
        QuizBean quiz1 = new QuizBean();
        quiz1.setQuiz_id(1);
        quiz1.setQuiz_title("노래 제목 맞추기");
        quiz1.setQuiz_text("이 노래의 제목은 무엇일까요?");
        quiz1.setQuiz_answer("소녀");
        quizzes.add(quiz1);

        QuizBean quiz2 = new QuizBean();
        quiz2.setQuiz_id(2);
        quiz2.setQuiz_title("가사 맞추기");
        quiz2.setQuiz_text("이 가사는 어떤 노래일까요?");
        quiz2.setQuiz_answer("사랑의 불시착");
        quizzes.add(quiz2);
    }
    

    // 모든 퀴즈를 반환하는 메서드
    public List<QuizBean> getAllQuizzes() {
    	System.out.println("현재 퀴즈 목록: " + quizzes);
        return new ArrayList<>(quizzes); // 새로운 리스트로 반환하여 원본 보호
    }

    // ID로 퀴즈를 가져오는 메서드
    public QuizBean getQuizById(int id) {
        return quizzes.stream()
                      .filter(quiz -> quiz.getQuiz_id() == id)
                      .findFirst()
                      .orElse(null); // 없을 경우 null 반환
    }
    
    // 정답 확인 메서드
    public boolean checkAnswer(int quizId, String answer) {
        QuizBean quiz = getQuizById(quizId);
        return quiz != null && quiz.getQuiz_answer().equalsIgnoreCase(answer);
    }
}
