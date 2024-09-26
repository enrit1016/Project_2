package kr.co.duck.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	private String saveUploadFile(MultipartFile upload_file) {
		String file_name=System.currentTimeMillis()+"_"+
				FilenameUtils.getBaseName(upload_file.getOriginalFilename()) + "." + 
				FilenameUtils.getExtension(upload_file.getOriginalFilename());
		
		try {
			upload_file.transferTo(new File(path_upload+"/"+file_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file_name;
	}

	public void addContent(ContentBean writeContentBean) {
		
		MultipartFile upload_file = writeContentBean.getUpload_file();
		
		if(upload_file.getSize() > 0) {
			String file_name = saveUploadFile(upload_file);
			writeContentBean.setImage(file_name);
		}
		
		writeContentBean.setMember_id(loginMemberBean.getMember_id());
		boardDao.addContentInfo(writeContentBean);
	}

	public List<ContentBean> getContentList(){
		return boardDao.getContentList();
	}

	public ContentBean getContentInfo(int content_idx) {
		return boardDao.getContentInfo(content_idx);
	}

	public List<ReplyBean> getReplyList(int content_id){
		return boardDao.getReplyList(content_id);
	}
	
	public String getBoardInfoName(int board_info_idx) {
		return boardDao.getBoardInfoName(board_info_idx);
	}

}
