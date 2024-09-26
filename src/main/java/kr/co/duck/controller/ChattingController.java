package kr.co.duck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.duck.service.QuizService;

@Controller
@RequestMapping
public class ChattingController {

	@Autowired
	private QuizService quizService;
	
	
	
}
