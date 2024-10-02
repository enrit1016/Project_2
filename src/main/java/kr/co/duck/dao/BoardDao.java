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

	public void addReply(ReplyBean writeReplyBean) {
		boardMapper.addReply(writeReplyBean);
	}
	
	public List<ContentBean> getContentList(){
		return boardMapper.getContentList();
	}

	public List<ContentBean> getBestList(){
		return boardMapper.getBestList();
	}

	public List<ContentBean> getsortedList(int board_id){
		return boardMapper.getsortedList(board_id);
	}

	public ContentBean getContentInfo(int boardpost_id) {
		return boardMapper.getContentInfo(boardpost_id);
	}

	public List<ReplyBean> getReplyList(int boardpost_id){
		return boardMapper.getReplyList(boardpost_id);
	}

	public void deleteContent(int boardpost_id) {
		boardMapper.deleteContent(boardpost_id);
	}

	public void deleteReply(int reply_id) {
		boardMapper.deleteReply(reply_id);
	}

	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}

	public int incrementLikeCount(int boardpost_id) {
        return boardMapper.incrementLikeCount(boardpost_id);
    }

    public int decrementLikeCount(int boardpost_id) {
        return boardMapper.decrementLikeCount(boardpost_id);
    }

    public int getLikeCount(int boardpost_id) {
        return boardMapper.getLikeCount(boardpost_id);
    }

}
