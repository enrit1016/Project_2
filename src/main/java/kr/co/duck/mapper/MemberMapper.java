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
	
	@Select("select membername, nickname from member where membername = #{membername} and password = #{password}")
	MemberBean getLoginMemberInfo(MemberBean tempLoginMemberBean);
	
	/*
	 * //구글 소셜 로그인정보 저장 //arg0:user_id //arg1:user_pw //arg2:user_email
	 * //arg3:user_tel //arg4:user_address //arg5:user_name //arg6:user_birthday
	 * 
	 * @Insert("insert into member values(member_seq.nextval, #{arg0},#{arg1},#{arg2},null,null,#{arg3},TO_DATE('2999-12-31', 'YYYY-MM-DD'),sysdate,'Google 회원')"
	 * ) void addGoogleUserInfo(String user_id, String user_pw, String user_email,
	 * String user_name);
	 * 
	 * @Select("select user_name, user_num from user_info where user_id = #{user_id} and user_pw = #{user_pw}"
	 * ) UserBean getLoginUserInfo(UserBean tempLoginUserBean);
	 * 
	 * @Select("select user_name, user_num from user_info where user_id = #{arg0}")
	 * UserBean getGoogleLoginUserInfo(String user_id);
	 */
	
	
}
