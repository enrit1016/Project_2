package kr.co.duck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.duck.service.MemberService;

@RestController
public class RestApiController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member/checkMemberNameExist/{membername}")
	public String checkMemberNameExist(@PathVariable String membername) {
		
		boolean chk = memberService.checkMemberNameExist(membername);
		
		return chk + "";
	}
	
}
