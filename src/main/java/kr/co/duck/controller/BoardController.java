package kr.co.duck.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.duck.beans.ContentBean;
import kr.co.duck.service.BoardService;

@Controller
@RequestMapping("/board")

public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/main")
	public String goToBoard() {
		return "board/main";
	}
	
	@GetMapping("/read")
	public String read() {
		return "board/read";
	}
	
	@GetMapping("/write")
	public String write(@ModelAttribute("writeContentBean")ContentBean writeContentBean) {
		return "board/write";
	}

	@PostMapping("/write_pro")
	public String write_pro(@Valid @ModelAttribute("writeContentBean")ContentBean writeContentBean, BindingResult result) {
		
		if(result.hasErrors()) {
			return "board/write";
		}
		
		
		boardService.addContent(writeContentBean);
		
		return "board/write_success";
	}

}
