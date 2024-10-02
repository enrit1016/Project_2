package kr.co.duck.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.duck.beans.MemberBean;
import kr.co.duck.service.MemberService;
import kr.co.duck.validator.MemberValidator;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Resource(name = "loginMemberBean")
	private MemberBean loginMemberBean;
	
	@GetMapping("/login")
	public String login(@ModelAttribute("tempLoginMemberBean") MemberBean tempLoginMemberBean,
						@RequestParam(value = "fail", defaultValue = "false") boolean fail,
						Model model) {
		model.addAttribute("fail", fail);
		
		return "member/login";
	}
	
	@PostMapping("/login")
	public String postlogin(@ModelAttribute("tempLoginMemberBean") MemberBean tempLoginMemberBean,
							@RequestParam(value = "fail", defaultValue = "false") boolean fail,
							Model model) {
		model.addAttribute("fail", fail);
		
		return "member/login";
	}
	
	@PostMapping("/login_pro")
	public String login_pro(@Valid @ModelAttribute("tempLoginMemberBean") MemberBean tempLoginMemberBean, 
							BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "member/login";
		}
		memberService.getLoginMemberInfo(tempLoginMemberBean);
		
		if(loginMemberBean.isMemberLogin() == true) {
			 // 로그인 성공 시 세션에 사용자 정보를 저장
	        session.setAttribute("loginMemberBean", loginMemberBean);
	        
			return "member/login_success";
		}else {
			return "member/login_fail";
		}
		
	}
	
	@GetMapping("/not_login")
	public String not_login() {
		return "member/not_login";
	}
	
	@GetMapping("/logout")
	public String logout() {
		loginMemberBean.setMemberLogin(false);
		
		return "member/logout";
	}
	
	
	@GetMapping("/join")
	public String join(@ModelAttribute("joinMemberBean") MemberBean joinMemberBean) {
		return "member/join";
	}
	
	@PostMapping("/join_pro")
	public String join_pro(@Valid @ModelAttribute("joinMemberBean") MemberBean joinMemberBean, BindingResult result) {
		
		if(result.hasErrors()) {
			return "member/join";
		}
		memberService.addMemberInfo(joinMemberBean);
		return "member/join_success";
	}
	
	@GetMapping("/info")
	public String info(@ModelAttribute("infoMemberBean") MemberBean infoMemberBean) {
		
		infoMemberBean = memberService.getModifyMemberInfo(infoMemberBean);
		
		return "member/info";
	}
	
	@PostMapping("/modify")
	public String modify(@ModelAttribute("modifyMemberBean") MemberBean modifyMemberBean) {
		
		modifyMemberBean = memberService.getModifyMemberInfo(modifyMemberBean);
		
		return "member/modify";
	}
	
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyMemberBean") MemberBean modifyMemberBean, BindingResult result,
							HttpSession session) {
		
		if(result.hasErrors()) {
			/*
			result.getAllErrors().forEach(error -> {
		        System.out.println("Error: " + error.getDefaultMessage());
		    }); //bindingresult 객체 오류 확인용
			*/
			return "member/modify_fail";
		}
		
		memberService.modifyMemberInfo(modifyMemberBean);
		
		//세션에 있는 로그인 된 회원 정보 갱신
		loginMemberBean.setAge(modifyMemberBean.getAge());
		loginMemberBean.setNickname(modifyMemberBean.getNickname());
		loginMemberBean.setReal_name(modifyMemberBean.getReal_name());
		
		//세션에 갱신된 객체 설정
		session.setAttribute("loginMemberBean", loginMemberBean);
		
		return "member/modify_success";
	}
	
	@GetMapping("/delete_account")
	public String deleteAccount(@ModelAttribute("deleteMemberBean") MemberBean deleteMemberBean) {
	    // 탈퇴 확인 화면을 렌더링 (비밀번호 입력 폼)
	    return "member/delete_account";
	}
	
	@PostMapping("/delete_pro")
	public String deleteMember(@ModelAttribute("deleteMemberBean") MemberBean deleteMemberBean, 
	                           Model model, HttpSession session) {

	    // 입력한 비밀번호가 현재 로그인된 회원의 비밀번호와 일치하는지 확인
	    boolean isPasswordCorrect = memberService.checkPassword(loginMemberBean.getMember_id(), deleteMemberBean.getPassword());
	        
	    if (!isPasswordCorrect) {
	        // 비밀번호가 일치하지 않으면 탈퇴 실패
	        model.addAttribute("fail", true);
	        return "member/delete_account";
	    }
	        
	    // 비밀번호가 일치하면 회원 탈퇴 처리
	    memberService.deleteMemberAccount(loginMemberBean.getMember_id());
	        
	    // 세션 무효화 (로그아웃 처리)
	    session.invalidate();
	        
	    return "member/delete_account_success"; // 탈퇴 성공 화면으로 이동
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		MemberValidator validator1 = new MemberValidator();
		binder.addValidators(validator1);
	}
	
	
}
