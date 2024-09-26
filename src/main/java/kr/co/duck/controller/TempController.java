package kr.co.duck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 임시 컨트롤러 클래스입니다. 완성 후 반드시 다른 컨트롤러 클래스로 옮겨주세요.

@Controller
@RequestMapping("/temp")
public class TempController {

	@GetMapping("/tempMain")
	public String main() {
		return "temp/tempMain";
	}
	
	@GetMapping("/SCSStest")
	public String testSCSS() {
		return "temp/SCSStest";
	}

	
	@GetMapping("/modalUI")
	public String testmodalUI() {
		return "temp/modalUI";
	}
}
