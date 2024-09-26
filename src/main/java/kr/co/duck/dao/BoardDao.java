package kr.co.duck.dao;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Repository;

import kr.co.duck.beans.ContentBean;
import kr.co.duck.mapper.BoardMapper;

@Repository
public class BoardDao {

	@Autowired
	private BoardMapper boardMapper;

	public void addContentInfo(ContentBean writeContentBean) {
				
		System.out.println(writeContentBean.getContent_title());
		System.out.println(writeContentBean.getContent_text());
		System.out.println(writeContentBean.getImage());
		System.out.println(writeContentBean.getBoard_id());
		System.out.println(writeContentBean.getMember_id());
		
		boardMapper.addContentInfo(writeContentBean);
	}
	
	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}
}
