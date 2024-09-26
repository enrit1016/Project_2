package kr.co.duck.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@PostMapping("/login_pro")
	public String login_pro(@Valid @ModelAttribute("tempLoginMemberBean") MemberBean tempLoginMemberBean, 
							BindingResult result) {
		if(result.hasErrors()) {
			return "member/login";
		}
		memberService.getLoginMemberInfo(tempLoginMemberBean);
		
		if(loginMemberBean.isMemberLogin() == true) {
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		MemberValidator validator1 = new MemberValidator();
		binder.addValidators(validator1);
	}
	
	
	/*
	 * @GetMapping("/checkMemberNameExist")
	 * 
	 * @ResponseBody public boolean
	 * checkMemberNameExist(@RequestParam(value="membername")String membername) {
	 * return true; }
	 */
	
	/*
	 * // 아이디 중복 체크
	 * 
	 * @GetMapping("/checkMemberNameExist/{membername}")
	 * 
	 * @ResponseBody public String checkMemberNameExist(@PathVariable("membername")
	 * String membername) { // 아이디 중복 확인 로직 (서비스 호출 등) boolean isExist =
	 * memberService.checkMemberNameExist(membername);
	 * 
	 * // 중복 여부에 따라 "true" 또는 "false" 반환 return isExist ? "true" : "false"; }
	 */
	
}
