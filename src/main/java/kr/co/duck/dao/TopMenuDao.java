package kr.co.duck.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.duck.beans.BoardBean;
import kr.co.duck.mapper.TopMenuMapper;

@Repository
public class TopMenuDao {

	@Autowired
	private TopMenuMapper topMenuMapper;
	
	public List<BoardBean> getTopList(){
		List<BoardBean> topMenuList = topMenuMapper.getTopMenuList();
		return topMenuList;
	}
	
}
