package kr.co.duck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.duck.service.MailSendService;
import kr.co.duck.service.MemberService;

@RestController
public class RestApiController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MailSendService mailService;
	
	@GetMapping("/member/checkMemberNameExist/{membername}")
	public String checkMemberNameExist(@PathVariable String membername) {
		
		boolean chk = memberService.checkMemberNameExist(membername);
		
		return chk + "";
	}
	
	
	 //회원정보 수정용 이메일 인증코드 발송
	 
	 @GetMapping("/member/modifyCertificationCode/{email}") 
	 public String modifyMailCheck(@PathVariable String email) {
	 System.out.println("모디파이 인증 요청 들어옴");
	 System.out.println("모디파이 인증 이메일 : "+email); return
	 mailService.modifyEmail(email); 
	 }
	 
	 
	
}
