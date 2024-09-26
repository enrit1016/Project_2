package kr.co.duck.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "song") // 데이터베이스의 테이블 이름
public class SongBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int song_id;

	private String title;
	private String artist;
	private String duration;
	private String genre;
	private String img;
	private String songid;
	private String video_id1;
	private String video_id1_1;
	private String video_id1_2;

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSongid() {
		return songid;
	}

	public void setSongid(String songid) {
		this.songid = songid;
	}

	public String getVideo_id1() {
		return video_id1;
	}

	public void setVideo_id1(String video_id1) {
		this.video_id1 = video_id1;
	}

	public String getVideo_id1_1() {
		return video_id1_1;
	}

	public void setVideo_id1_1(String video_id1_1) {
		this.video_id1_1 = video_id1_1;
	}

	public String getVideo_id1_2() {
		return video_id1_2;
	}

	public void setVideo_id1_2(String video_id1_2) {
		this.video_id1_2 = video_id1_2;
	}

}
