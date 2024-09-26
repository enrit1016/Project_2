package kr.co.duck.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.duck.beans.MemberBean;
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
	
}
