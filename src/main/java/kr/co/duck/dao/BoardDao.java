package kr.co.duck.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.duck.beans.ContentBean;
import kr.co.duck.beans.ReplyBean;
import kr.co.duck.mapper.BoardMapper;

@Repository
public class BoardDao {

	@Autowired
	private BoardMapper boardMapper;

	public void addContentInfo(ContentBean writeContentBean) {
		boardMapper.addContentInfo(writeContentBean);
	}
	
	public List<ContentBean> getContentList(){
		return boardMapper.getContentList();
	}

	public ContentBean getContentInfo(int content_idx) {
		return boardMapper.getContentInfo(content_idx);
	}

	public List<ReplyBean> getReplyList(int content_id){
		return boardMapper.getReplyList(content_id);
	}
	
	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}
}
