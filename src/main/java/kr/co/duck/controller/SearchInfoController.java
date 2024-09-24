package kr.co.duck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchInfoController {

	@GetMapping("/searchAlbum")
	public String searchAlbum() {
		return "search/searchAlbumInfo";
	}

	@GetMapping("/searchArtist")
	public String searchArtist() {
		return "search/searchArtistInfo";
	}

	@GetMapping("/searchMusic")
	public String searchMusic() {
		return "search/searchMusicInfo";
	}

}
