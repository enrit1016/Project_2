package kr.co.duck.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.duck.beans.MemberBean;
import kr.co.duck.domain.MemberGameStats;
import kr.co.duck.mapper.MemberMapper;

@Repository
public class MemberDao {

	@Autowired
	private MemberMapper memberMapper;
	
	public String checkMemberNameExist(String membername) {
		return memberMapper.checkMemberNameExist(membername);
	}
	
	public void addMemberInfo(MemberBean joinMemberBean) {
		memberMapper.addMemberInfo(joinMemberBean);
	}
	
	public MemberBean getLoginMemberInfo(MemberBean tempLoginMemberBean) {
		return memberMapper.getLoginMemberInfo(tempLoginMemberBean);
	}
	
	public String checkGoogleMemberNameExit(String membername) {
		return memberMapper.checkGoogleMemberNameExit(membername);
	}
	
	public void addGoogleMemberInfo(String membername, String password, String email, String nickname) {
		memberMapper.addGoogleMemberInfo(membername, password, email, nickname);
	}
	
	public MemberBean getGoogleLoginMemberInfo(String membername) {
		return memberMapper.getGoogleLoginMemberInfo(membername);
	}
	
	public MemberBean getModifyMemberInfo(int member_id) {
		return memberMapper.getModifyMemberInfo(member_id);
	}
	
	public void modifyMemberInfo(MemberBean modifyMemberBean) {
		memberMapper.modifyMemberInfo(modifyMemberBean);
	}
	
	public void deleteMemberAccount(int member_id) {
		memberMapper.deleteMemberAccount(member_id);
	}
	
	public String getMemberPassword(int member_id) {
		return memberMapper.getMemberPassword(member_id);
	}
	
	public void modifyMemberPassword(String password, String email) {
		memberMapper.modifyMemberPassword(password, email);
	}
	
	public int getGameScore(int member_id) {
		return memberMapper.getGameScore(member_id);
	}
	
}
