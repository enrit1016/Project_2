package kr.co.duck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.co.duck.beans.BoardBean;

public interface TopMenuMapper {

	@Select("select board_id, board_name from board order by board_id")
	List<BoardBean> getTopMenuList();
	
}
