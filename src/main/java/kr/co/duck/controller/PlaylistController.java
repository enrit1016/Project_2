package kr.co.duck.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.duck.beans.Song;
import kr.co.duck.service.PlaylistService;
import kr.co.duck.service.YouTubeService;

@Controller
@RequestMapping("/temp")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final YouTubeService youTubeService;

    @Autowired
    public PlaylistController(PlaylistService playlistService, YouTubeService youTubeService) {
        this.playlistService = playlistService;
        this.youTubeService = youTubeService;
    }

    @RequestMapping("/playlist")
    public String getPlaylist(Model model) {
        model.addAttribute("songs", playlistService.getPlaylist());
        return "temp/playlist"; // temp/playlist.jsp 경로
    }

    @PostMapping("/addSongFromYouTube")
    public String addSongFromYouTube(@RequestParam String query, Model model) {
        try {
            JSONArray videos = youTubeService.searchVideos(query);
            if (videos.length() > 0) {
                JSONObject video = videos.getJSONObject(0); // 첫 번째 검색 결과를 가져옴
                String title = video.getJSONObject("snippet").getString("title");
                String artist = "YouTube"; // 아티스트 정보를 직접 가져오기 어려우므로 기본값 사용

                // id 객체에서 videoId 또는 playlistId를 가져오기
                String url;
                if (video.getJSONObject("id").has("videoId")) {
                    String videoId = video.getJSONObject("id").getString("videoId");
                    url = "https://www.youtube.com/watch?v=" + videoId;
                } else if (video.getJSONObject("id").has("playlistId")) {
                    String playlistId = video.getJSONObject("id").getString("playlistId");
                    url = "https://www.youtube.com/playlist?list=" + playlistId;
                } else {
                    // videoId나 playlistId가 없으면 기본 URL 설정
                    url = "#";
                }

                Song newSong = new Song(0, title, artist, url); // ID는 나중에 데이터베이스에서 설정
                playlistService.addSong(newSong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("songs", playlistService.getPlaylist());
        return "temp/playlist"; // JSP로 결과 반환
    }

}
