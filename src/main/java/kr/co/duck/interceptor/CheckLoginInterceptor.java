package kr.co.duck.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.duck.beans.MemberBean;

public class CheckLoginInterceptor implements HandlerInterceptor{

	private MemberBean loginMemberBean;
	
	public CheckLoginInterceptor(MemberBean loginMemberBean) {
		this.loginMemberBean = loginMemberBean;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(loginMemberBean.isMemberLogin() == false) {
			String contextPath = request.getContextPath();
			//로그인이 되지 않았으므로 웹 브라우저에게 not_login 요청 지시
			response.sendRedirect(contextPath + "/member/not_login");
			return false;
		}
		
		return true;
	}
	
}
