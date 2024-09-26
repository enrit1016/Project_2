package kr.co.duck.service;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ManiaDBService {

	private static final String API_URL = "https://www.maniadb.com/api/search/%s/?sr=song&key=example&v=0.5";

	public List<Music> searchMusic(String query) {
		// API 호출 및 결과 가져오기 (XML 형식으로)
		String xmlResult = callManiaDBApi(query);

		// XML 결과가 비어 있거나 처리할 수 없는 경우 처리
		if (xmlResult.isEmpty()) {
			System.out.println("API 호출 실패 또는 응답이 비어 있습니다: " + xmlResult);
			return new ArrayList<>(); // 빈 리스트 반환
		}

		// XML을 List<Music>으로 변환
		return parseXmlToMusicList(xmlResult);
	}

	private String callManiaDBApi(String query) {
		RestTemplate restTemplate = new RestTemplate();
		String url = String.format(API_URL, query);
		try {
			return restTemplate.getForObject(url, String.class);
		} catch (Exception e) {
			e.printStackTrace(); // 예외 출력
			return ""; // 예외 발생 시 빈 문자열 반환
		}
	}

	private List<Music> parseXmlToMusicList(String xmlResult) {
		List<Music> musicList = new ArrayList<>();
		try {
			// Jsoup을 사용하여 XML 파싱
			xmlResult = xmlResult.replaceAll(":", "");
			xmlResult = xmlResult.replaceAll("//", "://");
			Document doc = Jsoup.parse(xmlResult, "", org.jsoup.parser.Parser.xmlParser());
			//System.out.println(doc);
			// XML에서 item 요소를 선택
			Elements items = doc.select("item");
			for (Element item : items) {
				// 각 item 요소에서 필요한 데이터를 추출
				String albumTitle = item.select("maniadbalbum title").text();				
				String title = item.select("title").text();
				title = title.replace(albumTitle, "");
				String link = item.select("link").text();
				String albumImage = item.select("image").text();
				String albumArtist = item.select("maniadbtrackartists maniadbartist name").text();
				
			
				// Music 객체 생성 및 데이터 설정
				Music music = new Music();
				music.setTitle(title);
				music.setLink(link);
				music.setAlbumTitle(albumTitle);
				music.setAlbumImage(albumImage);
				music.setAlbumArtist(albumArtist);

				
				// 리스트에 추가
				musicList.add(music);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return musicList;
	}

	// Music 클래스 정의
	public static class Music {
		private String title;
		private String runningtime;
		private String link;
		private String albumTitle;
		private String albumImage;
		private String description;
		private String albumArtist;
		private String albumRelease;
		
		// Getters and Setters
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getRunningtime() {
			return runningtime;
		}

		public void setRunningtime(String runningtime) {
			this.runningtime = runningtime;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getAlbumTitle() {
			return albumTitle;
		}

		public void setAlbumTitle(String albumTitle) {
			this.albumTitle = albumTitle;
		}

		public String getAlbumImage() {
			return albumImage;
		}

		public void setAlbumImage(String albumImage) {
			this.albumImage = albumImage;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getAlbumArtist() {
			return albumArtist;
		}

		public void setAlbumArtist(String albumArtist) {
			this.albumArtist = albumArtist;
		}

		public String getAlbumRelease() {
			return albumRelease;
		}

		public void setAlbumRelease(String albumRelease) {
			this.albumRelease = albumRelease;
		}

	}
}
