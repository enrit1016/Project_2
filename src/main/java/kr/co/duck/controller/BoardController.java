package kr.co.duck.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.duck.beans.ContentBean;
import kr.co.duck.beans.ReplyBean;
import kr.co.duck.service.BoardService;

@Controller
@RequestMapping("/board")

public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/main")
	public String goToBoard(Model model) {
		
		List<ContentBean> contentList = boardService.getContentList();
		model.addAttribute("contentList",contentList);
		
		return "board/main";
	}
	
	@GetMapping("/read")
	public String read(@RequestParam("content_id")int content_id, Model model) {
		
		model.addAttribute("content_id",content_id);
		
		ContentBean readContentBean =  boardService.getContentInfo(content_id);
		model.addAttribute("readContentBean",readContentBean);
		
		List<ReplyBean> replyList = boardService.getReplyList(content_id);
		model.addAttribute("replyList",replyList);
		
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

	@GetMapping("/modify")
	public String modify() {
		return "board/modify";
	}

}
