package kr.co.duck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.duck.beans.BoardBean;
import kr.co.duck.dao.TopMenuDao;

@Service
public class TopMenuService {

	@Autowired
	private TopMenuDao topMenuDao;
	
	public List<BoardBean> getTopMenuList(){
		List<BoardBean> topMenuList = topMenuDao.getTopList();
		return topMenuList;
	}
	
}
