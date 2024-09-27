package kr.co.duck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.duck.service.SearchService;

@Controller
@RequestMapping("/search")
public class SearchInfoController {

	@Autowired
	private SearchService searchService;
	
	
	@GetMapping("/searchAlbum")
	public String searchAlbum() {
		return "search/searchAlbumInfo";
	}

	@GetMapping("/searchMusic")
	public String searchMusic() {
		return "search/searchMusicInfo";
	}

}
