package kr.co.duck.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.co.duck.beans.ContentBean;
import kr.co.duck.beans.MemberBean;
import kr.co.duck.beans.ReplyBean;
import kr.co.duck.dao.BoardDao;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	@Resource(name = "loginMemberBean")
	private MemberBean loginMemberBean;

	@Value("${path.upload}")
	private String path_upload;

	public void addContent(ContentBean writeContentBean) {
		
		writeContentBean.setMember_id(loginMemberBean.getMember_id());
		boardDao.addContentInfo(writeContentBean);
	}

	public void addReply(ReplyBean writeReplyBean) {
		writeReplyBean.setMember_id(loginMemberBean.getMember_id());
		boardDao.addReply(writeReplyBean);
	}
	
	public List<ContentBean> getContentList(){
		return boardDao.getContentList();
	}
	
	public List<ContentBean> getBestList(){
		return boardDao.getBestList();
	}

	public List<ContentBean> getsortedList(int board_id){
		return boardDao.getsortedList(board_id);
	}
	
	public ContentBean getContentInfo(int boardpost_id) {
		return boardDao.getContentInfo(boardpost_id);
	}

	public List<ReplyBean> getReplyList(int boardpost_id){
		return boardDao.getReplyList(boardpost_id);
	}
	
	public void deleteContent(int boardpost_id) {
		boardDao.deleteContent(boardpost_id);
	}
	
	public void deleteReply(int reply_id) {
		boardDao.deleteReply(reply_id);
	}
	
	public String getBoardInfoName(int board_info_idx) {
		return boardDao.getBoardInfoName(board_info_idx);
	}

	public int addLike(int boardpost_id) {
        return boardDao.incrementLikeCount(boardpost_id);
    }

    public int removeLike(int boardpost_id) {
        return boardDao.decrementLikeCount(boardpost_id);
    }
	
	public int getLikeCount(int boardpost_id) {
		Integer count = boardDao.getLikeCount(boardpost_id);
		return count != null ? count : 0;
	}
}
