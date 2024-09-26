package kr.co.duck.mapper;

import org.apache.ibatis.annotations.Insert; 
import org.apache.ibatis.annotations.Select;

import kr.co.duck.beans.ContentBean;

public interface BoardMapper {

	@Insert("insert into content (content_id, content_title, content_text, image, write_date, board_id, member_id) "
			+ "values (content_seq.nextval, #{content_title}, #{content_text}, #{image, jdbcType=VARCHAR}, sysdate, #{board_id}, #{member_id})")
	void addContentInfo(ContentBean writeContentBean);

	@Select("select board_name "
			+ "from board "
			+ "where board_id=#{board_id}")
	String getBoardInfoName(int board_id);
}
