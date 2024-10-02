package kr.co.duck.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.duck.beans.ContentBean;
import kr.co.duck.beans.MemberBean;
import kr.co.duck.beans.ReplyBean;
import kr.co.duck.service.BoardService;

@Controller
@RequestMapping("/board")

public class BoardController {

	@Autowired
	private BoardService boardService;

	@Resource(name = "loginMemberBean")
	private MemberBean loginMemberBean;

	@GetMapping("/main")
	public String goToBoard(Model model) {
		
		List<ContentBean> contentList = boardService.getContentList();
		model.addAttribute("contentList",contentList);
		
		List<ContentBean> bestList = boardService.getBestList();
		model.addAttribute("bestList",bestList);
		
		return "board/main";
	}

	@GetMapping("/main_sort")
	public String sortMain(@RequestParam("board_id")int board_id, Model model) {
		
		List<ContentBean> sortedContentList = boardService.getsortedList(board_id);
		model.addAttribute("contentList",sortedContentList);
		
		return "board/main";
	}

	@GetMapping("/read")
	public String read(@RequestParam("boardpost_id")int boardpost_id, Model model) {
		
		model.addAttribute("boardpost_id",boardpost_id);
		
		ContentBean readContentBean =  boardService.getContentInfo(boardpost_id);
		model.addAttribute("readContentBean",readContentBean);
		
		model.addAttribute("loginMemberBean",loginMemberBean);
		
		List<ReplyBean> replyList = boardService.getReplyList(boardpost_id);
		model.addAttribute("replyList",replyList);
		
		List<ContentBean> bestList = boardService.getBestList();
		model.addAttribute("bestList",bestList);
		
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

	@PostMapping("write_reply_pro")
	public String write_reply_pro(@Valid @ModelAttribute("writeReplyBean")ReplyBean writeReplyBean,
								  @RequestParam("boardpost_id") int boardpost_id,
								  BindingResult result, Model model) {
		if(result.hasErrors()) {	
			return "board/read";
		}
		
		boardService.addReply(writeReplyBean);
		
		return "redirect:/board/read?boardpost_id=" + boardpost_id;
	}

	//보류
	@GetMapping("/modify")
	public String modify(@RequestParam("boardpost_id")int boardpost_id,
						 @Valid @ModelAttribute("modifyContentBean")ContentBean modifyContentBean,
						 Model model) {
		
		model.addAttribute("boardpost_id",boardpost_id);
		
		return "board/modify";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("boardpost_id")int boardpost_id,
						 Model model) {
		
		boardService.deleteContent(boardpost_id);
		model.addAttribute("boardpost_id",boardpost_id);

		return "board/delete";
	}

	@GetMapping("/delete_rep")
	public String delete_rep(@RequestParam("reply_id")int reply_id,
							 @RequestParam("boardpost_id") int boardpost_id,
							 Model model) {
		
		boardService.deleteReply(reply_id);
		model.addAttribute("reply_id",reply_id);

		return "redirect:/board/read?boardpost_id=" + boardpost_id;
	}

	@PostMapping("/add_like")
	@ResponseBody
	public Map<String, Object> addLike(@RequestBody Map<String, Integer> requestBody) {
		Integer boardpost_id = requestBody.get("boardpost_id"); // JSON에서 boardpost_id 추출
		Integer member_id = requestBody.get("member_id"); // JSON에서 member_id 추출
		
	    System.out.println("Received boardpost_id: " + boardpost_id);
	    System.out.println("Received member_id: " + member_id);

	    boardService.addLike(boardpost_id);

	    Map<String, Object> response = new HashMap<>();
	    response.put("success", true);
	    response.put("newLikeCount", boardService.getLikeCount(boardpost_id));

	    return response; // JSON 형태로 응답
	}

	@PostMapping("/remove_like")
	@ResponseBody
	public Map<String, Object> removeLike(@RequestBody Map<String, Integer> requestBody) {
	    Integer boardpost_id = requestBody.get("boardpost_id"); // JSON에서 boardpost_id 추출
		Integer member_id = requestBody.get("member_id"); // JSON에서 member_id 추출

	    System.out.println("Received boardpost_id: " + boardpost_id);
	    System.out.println("Received member_id: " + member_id);

	    boardService.removeLike(boardpost_id);

	    Map<String, Object> response = new HashMap<>();
	    response.put("success", true);
	    response.put("newLikeCount", boardService.getLikeCount(boardpost_id));

	    return response; // JSON 형태로 응답
	}
   
}
