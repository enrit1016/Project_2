package kr.co.duck.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import kr.co.duck.beans.PageBean;
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
	public String goToBoard(@RequestParam(value = "page", defaultValue = "1") int page,
							Model model) {
		
		Date currentDate = new Date();
		String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
		model.addAttribute("currentDate", formattedDate);
		
		model.addAttribute("loginMemberBean", loginMemberBean);
		
		List<ContentBean> contentList = boardService.getContentList(page);
		model.addAttribute("contentList",contentList);
		
		List<ContentBean> bestList = boardService.getBestList();
		model.addAttribute("bestList",bestList);
		
		PageBean pageBean = boardService.getContentCnt(page);
		model.addAttribute("pageBean",pageBean);
		
		return "board/main";
	}

	@GetMapping("/main_sort")
	public String sortMain(@RequestParam("board_id") int board_id,
	                       @RequestParam(value = "page", defaultValue = "1") int page,
	                       HttpSession session, HttpServletRequest request, Model model) {
	    System.out.println("sortMain method called with board_id: " + board_id);  // 로그로 메서드 호출 확인

	    // 로그인 여부 체크
	    if (loginMemberBean == null || !loginMemberBean.isMemberLogin()) {
	        // 현재 URL을 세션에 저장하여 로그인 후 돌아갈 수 있도록 설정
	        String currentUrl = request.getRequestURI() + "?" + request.getQueryString(); // 현재 URL 가져오기
	        session.setAttribute("redirectURI", currentUrl);  // 로그인 후 돌아올 URI 저장
	        System.out.println("Saved redirectURI: " + currentUrl);  // 로그로 확인
	        return "redirect:/member/login";
	    }

	    // 로그인 상태라면 게시글 리스트 가져오기
	    List<ContentBean> sortedContentList = boardService.getsortedList(board_id, page);
	    model.addAttribute("contentList", sortedContentList);

	    List<ContentBean> bestList = boardService.getBestList();
		model.addAttribute("bestList",bestList);
		
	    // 페이징 처리
	    PageBean pageBean = boardService.getSortedContentCnt(board_id, page);
	    model.addAttribute("pageBean", pageBean);

	    // board_id도 모델에 저장
	    model.addAttribute("board_id", board_id);

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
	public String write(@ModelAttribute("writeContentBean")ContentBean writeContentBean, Model model) {
		model.addAttribute("loginMemberBean",loginMemberBean);
		return "board/write";
	}

	@PostMapping("/write_pro")
	public String write_pro(@Valid @ModelAttribute("writeContentBean")ContentBean writeContentBean, BindingResult result) {
		
		if(result.hasErrors()) {
			return "board/write";
		}
		
		//더미데이터 넣을때 사용
		//for (int i = 0; i < 100; i++) {
		boardService.addContent(writeContentBean);
		//}
		return "board/write_success";
	}

	@PostMapping("write_reply_pro")
	public String write_reply_pro(@Valid @ModelAttribute("writeReplyBean")ReplyBean writeReplyBean,
								  @RequestParam("boardpost_id") int boardpost_id,
								  BindingResult result, Model model) {
		if(result.hasErrors()) {	
			return "board/read";
		}
		
		String replyText = writeReplyBean.getReply_text();
		replyText = replyText.replaceAll("\n", "<br>");
		writeReplyBean.setReply_text(replyText);
		
		boardService.addReply(writeReplyBean);
		
		return "redirect:/board/read?boardpost_id=" + boardpost_id;
	}

	@GetMapping("/modify")
	public String modify(@RequestParam("boardpost_id")int boardpost_id,
						 Model model) {
		
		ContentBean tempContentBean = boardService.getContentInfo(boardpost_id);
		ContentBean modifyContentBean = new ContentBean();
		
		modifyContentBean.setMembername(tempContentBean.getMembername());
		modifyContentBean.setWritedate(tempContentBean.getWritedate());
		modifyContentBean.setContent_title(tempContentBean.getContent_title());
		modifyContentBean.setContent_text(tempContentBean.getContent_text());
		modifyContentBean.setMember_id(tempContentBean.getMember_id());
		modifyContentBean.setBoardpost_id(boardpost_id);
		
		model.addAttribute("boardpost_id",boardpost_id);
		model.addAttribute("modifyContentBean", modifyContentBean);
		return "board/modify";
	}

	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyContentBean")ContentBean modifyContentBean,
							 BindingResult result,
			 				 Model model) {
		
		if(result.hasErrors()) {
			return "board/modify";
		}
		
		boardService.modifyContentInfo(modifyContentBean);
		return "board/modify_success";
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

	    boardService.removeLike(boardpost_id);

	    Map<String, Object> response = new HashMap<>();
	    response.put("success", true);
	    response.put("newLikeCount", boardService.getLikeCount(boardpost_id));

	    return response; // JSON 형태로 응답
	}

	@GetMapping("/search")
	public String searchPosts(@RequestParam("query") String query, 
							  @RequestParam(value = "board_id", required = false) int boardId,
							  @RequestParam(value = "page", defaultValue = "1") int page,
							  Model model) {
	    model.addAttribute("board_id",boardId);
	    
	    List<ContentBean> bestList = boardService.getBestList();
		model.addAttribute("bestList",bestList);
		
	    if (boardId == 0) {
	    	PageBean pageBean = boardService.getAllSearchedContentCnt(query, page);
			model.addAttribute("pageBean",pageBean);
			
		    List<ContentBean> searchResults = boardService.searchAllPosts(query, page);
		    model.addAttribute("contentList", searchResults);
	    } else {
	    	PageBean pageBean = boardService.getSearchedContentCnt(boardId, query, page);
			model.addAttribute("pageBean",pageBean);
			
		    List<ContentBean> searchResults = boardService.searchPosts(boardId, query, page);
		    model.addAttribute("contentList", searchResults);
	    }
	    
	    return "board/main";
	}

}
