package kr.co.duck.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.co.duck.beans.MemberBean;

public interface MemberMapper {

	@Select("select nickname from member where membername = #{membername}")
	String checkMemberNameExist(String membername);

	@Insert("insert into member values "
			+ "(member_seq.nextval, #{membername}, #{password}, #{age}, #{email}, TO_CHAR(sysdate,'YYYY-MM-dd'), #{real_name}, 'User', #{nickname}, 'Duck 회원')")
	void addMemberInfo(MemberBean joinMemberBean);

	@Select("select member_id, nickname from member where membername = #{membername} and password = #{password}")
	MemberBean getLoginMemberInfo(MemberBean tempLoginMemberBean);

	@Select("select nickname from member where membername = #{arg0}")
	String checkGoogleMemberNameExit(String membername);
	
	// 구글 소셜 로그인정보 저장 //arg0:membername //arg1:password //arg2:email arg3:nickname 

	@Insert("insert into member values(member_seq.nextval, #{arg0},#{arg1},'null',#{arg2},TO_CHAR(sysdate,'YYYY-MM-dd'),#{arg3},'User',#{arg3},'Google 회원')")
	void addGoogleMemberInfo(String membername, String password, String email, String nickname);

	@Select("select member_id, nickname from member where membername = #{arg0}")
	MemberBean getGoogleLoginMemberInfo(String membername);
	
	@Select("select membername, age, email, join_date, real_name, nickname, logintype from member where member_id = #{member_id}")
	MemberBean getModifyMemberInfo(int member_id);

}
