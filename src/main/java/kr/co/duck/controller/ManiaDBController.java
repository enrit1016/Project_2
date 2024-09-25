package kr.co.duck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.duck.service.ManiaDBService;
import kr.co.duck.service.ManiaDBService.Music;

import java.util.List;

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
                                @RequestParam(value = "searchType", defaultValue = "song") String searchType, 
                                Model model) {

        // searchType에 따른 데이터 검색 수행
        List<?> searchResult = maniaDBService.searchMusic(query, searchType);

        // 검색 결과를 모델에 추가
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("searchType", searchType);  // 검색 유형도 추가 (song, artist, album)

        // 검색 결과를 포함한 페이지를 다시 렌더링
        return "temp/maniadbSearch"; // JSP 파일의 위치
    }
}
