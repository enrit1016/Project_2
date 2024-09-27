package kr.co.duck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert; 
import org.apache.ibatis.annotations.Select;

import kr.co.duck.beans.ContentBean;
import kr.co.duck.beans.ReplyBean;

public interface BoardMapper {

	@Insert("insert into content (content_id, content_title, content_text, image, write_date, board_id, member_id) "
			+ "values (content_seq.nextval, #{content_title}, #{content_text}, #{image, jdbcType=VARCHAR}, sysdate, #{board_id}, #{member_id})")
	void addContentInfo(ContentBean writeContentBean);

	@Select("select c.content_id, b.board_name, c.content_title, m.membername, to_char(c.write_date, 'yyyy-mm-dd') as write_date, c.like_count "
			+ "from content c "
			+ "join board b on c.board_id = b.board_id "
			+ "join member m on c.member_id = m.member_id ")
	List<ContentBean> getContentList();
	
	@Select("select m.membername, to_char(r.reply_date, 'yyyy-mm-dd') as reply_date, r.reply_text "
			+ "from reply r "
			+ "join content c on r.content_id = c.content_id "
			+ "join member m on r.member_id = m.member_id "
			+ "where c.content_id=#{content_id}")
	List<ReplyBean> getReplyList(int content_id);

	@Select("select c.content_title, m.membername, to_char(c.write_date, 'yyyy-mm-dd') as write_date, c.content_text, c.image, c.like_count "
			+ "from content c "
			+ "join board b on c.board_id = b.board_id "
			+ "join member m on c.member_id = m.member_id "
			+ "where c.content_id=#{content_id}")
	ContentBean getContentInfo(int content_idx);

	@Select("select board_name "
			+ "from board "
			+ "where board_id=#{board_id}")
	String getBoardInfoName(int board_id);
	
}
