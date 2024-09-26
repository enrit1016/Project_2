package kr.co.duck.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.duck.beans.MemberBean;
import kr.co.duck.dao.MemberDao;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Resource(name = "loginMemberBean")
	private MemberBean loginMemberBean;
	
	public boolean checkMemberNameExist(String membername) {
		String member_name = memberDao.checkMemberNameExist(membername);
		
		if(member_name == null) {
			return true; //=아이디 사용할 수 있음
		}else {
			return false; //=아이디 사용할 수 없음
		}
	}
	
	public void addMemberInfo(MemberBean joinMemberBean) {
		memberDao.addMemberInfo(joinMemberBean);
	}
	
	public void getLoginMemberInfo(MemberBean tempLoginMemberBean) {
		MemberBean tempLoginMemberBean2 = memberDao.getLoginMemberInfo(tempLoginMemberBean);
		
		if(tempLoginMemberBean2 != null) {
			loginMemberBean.setMember_id(tempLoginMemberBean2.getMember_id());			
			loginMemberBean.setNickname(tempLoginMemberBean2.getNickname());
			loginMemberBean.setMemberLogin(true);
		}
	}
	
	public boolean checkGoogleMemberNameExist(String membername) {
		String member_name = memberDao.checkGoogleMemberNameExit(membername);
		
		if(member_name == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public void addGoogleMemberInfo(String membername, String password, String email, String nickname) {
		
		if(checkGoogleMemberNameExist(membername)) {
			memberDao.addGoogleMemberInfo(membername, password, email, nickname);
		}
		
		MemberBean tempLoginMemberBean3 = memberDao.getGoogleLoginMemberInfo(membername);
		
		if(tempLoginMemberBean3 != null) {
			loginMemberBean.setMember_id(tempLoginMemberBean3.getMember_id());
			loginMemberBean.setMembername(tempLoginMemberBean3.getMembername());
			loginMemberBean.setNickname(tempLoginMemberBean3.getNickname());
			loginMemberBean.setEmail(tempLoginMemberBean3.getEmail());
			loginMemberBean.setMemberLogin(true);
		}
	}
	
	
	// 유저 정보 수정
	public MemberBean getModifyUserInfo(MemberBean modifyMemberBean) {
		
		MemberBean tempModifyMemberBean = memberDao.getModifyMemberInfo(loginMemberBean.getMember_id());
		modifyMemberBean.setMembername(tempModifyMemberBean.getMembername());
		modifyMemberBean.setAge(tempModifyMemberBean.getAge());
		modifyMemberBean.setEmail(tempModifyMemberBean.getEmail());
		modifyMemberBean.setReal_name(tempModifyMemberBean.getReal_name());
		modifyMemberBean.setNickname(tempModifyMemberBean.getNickname());
		modifyMemberBean.setJoin_date(tempModifyMemberBean.getJoin_date());
		modifyMemberBean.setLogintype(tempModifyMemberBean.getLogintype());
		
		return modifyMemberBean;
	}
	
}
