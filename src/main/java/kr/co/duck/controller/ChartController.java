package kr.co.duck.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChartController {

    @RequestMapping("/melon-chart")
    public String getMelonChart(Model model) {
        List<Song> songs = new ArrayList<>();

        try {
            String url = "https://www.melon.com/chart/index.htm";
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .get();

            Elements rows = doc.select("table tbody tr");

            for (Element row : rows) {
                String title = row.select(".ellipsis.rank01 a").text(); // 곡 제목
                String artist = row.select(".checkEllipsis a").text(); // 아티스트 (중복 제거)
                String album = row.select(".ellipsis.rank03 a").text(); // 앨범명
                String rank = row.select(".rank").text(); // 순위
                songs.add(new Song(rank, title, artist, album)); // Song 객체 생성 및 리스트에 추가
            }

            System.out.println("Number of songs: " + songs.size());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch data from Melon.");
        }

        model.addAttribute("songs", songs); // JSP로 데이터 전달
        return "temp/melonChart"; // melonChart.jsp로 이동
    }

    // Song 클래스: 멜론 차트 정보를 담는 객체
    public static class Song {
        private String rank;
        private String title;
        private String artist;
        private String album;

        public Song(String rank, String title, String artist, String album) {
            this.rank = rank;
            this.title = title;
            this.artist = artist;
            this.album = album;
        }

        public String getRank() {
            return rank;
        }

        public String getTitle() {
            return title;
        }

        public String getArtist() {
            return artist;
        }

        public String getAlbum() {
            return album;
        }
    }
}
