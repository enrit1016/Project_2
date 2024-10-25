package kr.co.duck.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.duck.beans.MemberBean;
import kr.co.duck.domain.MemberGameStats;

public interface MemberMapper {

	@Select("select nickname from member where membername = #{membername}")
	String checkMemberNameExist(String membername);

	@Insert("insert into member values "
			+ "(member_seq.nextval, #{membername}, #{password}, #{birthday}, #{email}, TO_CHAR(sysdate,'YYYY-MM-dd'), #{real_name}, #{nickname}, 'Duck 회원')")
	void addMemberInfo(MemberBean joinMemberBean);

	//real_name, email(해진 추가)
	@Select("select member_id, nickname, real_name, email from member where membername = #{membername} and password = #{password}")
	MemberBean getLoginMemberInfo(MemberBean tempLoginMemberBean);

	@Select("select nickname from member where membername = #{arg0}")
	String checkGoogleMemberNameExit(String membername);
	
	// 구글 소셜 로그인정보 저장 //arg0:membername //arg1:password //arg2:email arg3:nickname 

	@Insert("insert into member values(member_seq.nextval, #{arg0},#{arg1},'null',#{arg2},TO_CHAR(sysdate,'YYYY-MM-dd'),#{arg3},#{arg3},'Google 회원')")
	void addGoogleMemberInfo(String membername, String password, String email, String nickname);

	@Select("select member_id, nickname from member where membername = #{arg0}")
	MemberBean getGoogleLoginMemberInfo(String membername);
	
	@Select("select membername, birthday, email, join_date, real_name, nickname, logintype from member where member_id = #{member_id}")
	MemberBean getModifyMemberInfo(int member_id);
	
	@Update("update member set birthday = #{birthday}, email = #{email}, real_name = #{real_name}, nickname = #{nickname} where member_id = #{member_id}")
	void modifyMemberInfo(MemberBean modifyMemberBean);
	
	@Delete("delete from member where member_id = #{member_id}")
	void deleteMemberAccount(int member_id);
	
	@Select("select password from member where member_id = #{member_id}")
	String getMemberPassword(int member_id);
	
	@Update("update member set password = #{password} where email = #{email}")
	void modifyMemberPassword(@Param("password") String password, @Param("email") String email);
	
	@Select("select COALESCE(s.score, 0) from member m LEFT JOIN member_game_stats s on m.member_id = s.member_id where m.member_id = #{member_id}")
	Integer getGameScore(int member_id);

}
