package kr.co.duck.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.duck.beans.BoardBean;
import kr.co.duck.beans.MemberBean;
import kr.co.duck.service.TopMenuService;


public class TopMenuInterceptor implements HandlerInterceptor{

	private TopMenuService topMenuService;
	private MemberBean loginMemberBean;
	
	//생성자 통해 주입
	public TopMenuInterceptor(TopMenuService topMenuService, MemberBean loginMemberBean) {
		this.topMenuService = topMenuService;
		this.loginMemberBean = loginMemberBean;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//상단 메뉴 목록 가져옴
		List<BoardBean> topMenuList = topMenuService.getTopMenuList();
		request.setAttribute("topMenuList", topMenuList);
		request.setAttribute("loginMemberBean", loginMemberBean);
		
		return true;
	}
	
}
