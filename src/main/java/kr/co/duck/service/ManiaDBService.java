package kr.co.duck.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ManiaDBService {

	private static final String API_URL = "https://www.maniadb.com/api/search/%s/?sr=%s&key=example&v=0.5";

	public List<?> searchMusic(String query, String searchType) {
		// API 호출 및 결과 가져오기 (XML 형식으로)
		String xmlResult = callManiaDBApi(query, searchType);
		// XML 결과가 비어 있거나 처리할 수 없는 경우 처리
		if (xmlResult.isEmpty()) {
			System.out.println("API 호출 실패 또는 응답이 비어 있습니다: " + xmlResult);
			return new ArrayList<>(); // 빈 리스트 반환
		}

		// searchType에 따른 처리
		switch (searchType.toLowerCase()) {
		case "artist":
			return parseXmlToArtistList(xmlResult);
		case "album":
			return parseXmlToAlbumList(xmlResult);
		case "song":
		default:
			return parseXmlToMusicList(xmlResult);
		}
	}

	private String callManiaDBApi(String query, String searchType) {
		RestTemplate restTemplate = new RestTemplate();
		String url = String.format(API_URL, query, searchType);
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

			// XML에서 item 요소를 선택
			Elements items = doc.select("item");
			for (Element item : items) {
				String albumTitle = item.select("maniadbalbum title").text();
				String title = item.select("title").text();
				title = title.replace(albumTitle, "");
				String guid = item.select("guid").text();
				String albumImage = item.select("image").text();
				String albumArtist = item.select("maniadbtrackartists maniadbartist name").text();

				// Music 객체 생성 및 데이터 설정
				Music music = new Music();
				music.setTitle(title);
				music.setGuid(guid);
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

	private List<Artist> parseXmlToArtistList(String xmlResult) {
		List<Artist> artistList = new ArrayList<>();
		try {
			// Jsoup을 사용하여 XML 파싱
			xmlResult = xmlResult.replaceAll(":", "");
			xmlResult = xmlResult.replaceAll("//", "://");
			Document doc = Jsoup.parse(xmlResult, "", org.jsoup.parser.Parser.xmlParser());
			// XML에서 item 요소를 선택
			Elements items = doc.select("item");
			for (Element item : items) {
				String artistName = item.select("title").text();
				String period = item.select("period").text();
				String image = item.select("image").text();
				String majorsonglistRaw = item.select("maniadbmajorsonglist").text();
				String relatedartistlist = item.select("maniadbrelatedartistlist").text();
				String description = item.select("description").text();
				String link = item.select("link").text();
				System.out.println(link);
				// 트랙 리스트를 분할하여 저장
				String[] majorsonglistArray = majorsonglistRaw.split(" / ");
				List<String> majorSongList = Arrays.asList(majorsonglistArray);

				// Artist 객체 생성 및 데이터 설정
				Artist artist = new Artist();
				artist.setArtistName(artistName);
				artist.setPeriod(period);
				artist.setImage(image);
				artist.setMajorSongList(majorSongList);
				artist.setRelativDartistList(relatedartistlist);
				artist.setDescription(description);
				artist.setLink(link);

				// 리스트에 추가
				artistList.add(artist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return artistList;
	}

	private List<Album> parseXmlToAlbumList(String xmlResult) {
		List<Album> albumList = new ArrayList<>();
		try {
			// Jsoup을 사용하여 XML 파싱
			xmlResult = xmlResult.replaceAll(":", "");
			xmlResult = xmlResult.replaceAll("//", "://");
			xmlResult = xmlResult.replace("[Disc 1]", "");
			xmlResult = xmlResult.replace("[Disc 2]", " / [Disc 2]");
			Document doc = Jsoup.parse(xmlResult, "", org.jsoup.parser.Parser.xmlParser());

			// XML에서 item 요소를 선택
			Elements items = doc.select("item");
			for (Element item : items) {
				String albumArtist = item.select("maniadbartist name").text();
				String albumName = item.select("title").text();
				String releaseCompany = item.select("release_company").text();
				String albumimage = item.select("front image").text();
				String trackListRaw = item.select("maniadbtracklist").text();
				String description = item.select("description").text();
				String guid = item.select("guid").text();

				// 트랙 리스트를 '/' 단위로 분할하여 배열로 저장
				String[] trackListArray = trackListRaw.split(" / ");
				List<String> trackList = Arrays.asList(trackListArray);

				// Album 객체 생성 및 데이터 설정
				Album album = new Album();
				album.setAlbumArtist(albumArtist);
				album.setAlbumName(albumName);
				album.setReleaseCompany(releaseCompany);
				album.setAlbumimage(albumimage);
				album.setTrackList(trackList);
				album.setDescription(description);
				album.setGuid(guid);
				
				// 리스트에 추가
				albumList.add(album);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return albumList;
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
		private String guid; //정보를 가진 페이지로 넘어가는 링크

		public String getGuid() {
			return guid;
		}

		public void setGuid(String guid) {
			this.guid = guid;
		}

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

	// Artist 클래스 정의
	public static class Artist {
		private String artistName;
		private String period;
		private String image;
		private List<String> majorSongList;
		private String relativDartistList;
		private String description;
		private String link; //정보를 가진 페이지로 넘어가는 링크
		
		
		
		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getArtistName() {
			return artistName;
		}

		public void setArtistName(String artistName) {
			this.artistName = artistName;
		}

		public String getPeriod() {
			return period;
		}

		public void setPeriod(String period) {
			this.period = period;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public List<String> getMajorSongList() {
			return majorSongList;
		}

		public void setMajorSongList(List<String> majorSongList) {
			this.majorSongList = majorSongList;
		}

		public String getRelativDartistList() {
			return relativDartistList;
		}

		public void setRelativDartistList(String relativDartistList) {
			this.relativDartistList = relativDartistList;
		}

	}

	// Album 클래스 정의
	public static class Album {
		private String albumName;
		private String albumArtist;
		private String releaseCompany;
		private List<String> trackList;
		private String albumimage;
		private String description;
		private String guid; //정보를 가진 페이지로 넘어가는 링크

		
		
		public String getGuid() {
			return guid;
		}

		public void setGuid(String guid) {
			this.guid = guid;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getAlbumName() {
			return albumName;
		}

		public void setAlbumName(String albumName) {
			this.albumName = albumName;
		}

		public String getAlbumArtist() {
			return albumArtist;
		}

		public void setAlbumArtist(String albumArtist) {
			this.albumArtist = albumArtist;
		}

		public String getReleaseCompany() {
			return releaseCompany;
		}

		public void setReleaseCompany(String releaseCompany) {
			this.releaseCompany = releaseCompany;
		}

		public List<String> getTrackList() {
			return trackList;
		}

		public void setTrackList(List<String> trackList) {
			this.trackList = trackList;
		}

		public String getAlbumimage() {
			return albumimage;
		}

		public void setAlbumimage(String albumimage) {
			this.albumimage = albumimage;
		}

	}

}
