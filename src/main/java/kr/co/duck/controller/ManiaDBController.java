package kr.co.duck.controller;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.duck.service.ManiaDBService;

@Controller
public class ManiaDBController {

	@Autowired
	private ManiaDBService maniaDBService;

	// 검색 화면을 표시하는 매핑
	@GetMapping("/temp/maniadbSearch")
	public String showSearchPage() {
		return "temp/maniadbSearch"; // JSP 파일의 위치
	}

	// 검색 요청을 처리하는 매핑
	@GetMapping("/temp/search")
	public String searchManiaDB(@RequestParam("query") String query,
			@RequestParam(value = "searchType", defaultValue = "song") String searchType, Model model) {

		// searchType에 따른 데이터 검색 수행
		List<?> searchResult = maniaDBService.searchMusic(query, searchType);

		// 검색 결과를 모델에 추가
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("searchType", searchType); // 검색 유형도 추가 (song, artist, album)

		// 검색 결과를 포함한 페이지를 다시 렌더링
		return "temp/maniadbSearch"; // JSP 파일의 위치
	}

	
	//상세페이지로 넘어가는 요청을 처리하는 매핑
	@GetMapping("temp/parseDetail")
	public String parseDetail(@RequestParam("guid") String guid, @RequestParam("type") String type, Model model) {
		try {
			// Jsoup을 사용해 URL에 있는 정보를 파싱
			Object result = maniaDBService.scrapeDetail(guid, type);
			
			System.out.println(guid);
			System.out.println(type);
			// 모델에 데이터를 추가
			model.addAttribute("result", result);

			// 각 type에 따라 적절한 JSP로 이동
			if ("artist".equalsIgnoreCase(type)) {
				return "search/searchArtistInfo";
			} else {
				return "search/searchAlbumInfo";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "temp/maniadbSearch"; // 예외 발생 시 에러 페이지로 이동
		}

	}
}
