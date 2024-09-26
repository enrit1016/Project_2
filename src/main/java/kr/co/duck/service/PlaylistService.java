package kr.co.duck.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.duck.beans.Song;

@Service
public class PlaylistService {
	private List<Song> playlist;

	public PlaylistService() {
		this.playlist = new ArrayList<>();
		// 예시 데이터
		playlist.add(new Song(1,"Song 1", "Artist 1", "/music/song1.mp3"));
		playlist.add(new Song(2,"Song 2", "Artist 2", "/music/song2.mp3"));
		playlist.add(new Song(3,"Song 3", "Artist 3", "/music/song3.mp3"));
		playlist.add(new Song(4,"Song 4", "Artist 4", "/music/song4.mp3"));
		playlist.add(new Song(5,"Song 5", "Artist 5", "/music/song5.mp3"));
		playlist.add(new Song(6,"Song 6", "Artist 6", "/music/song6.mp3"));
		playlist.add(new Song(7,"Song 7", "Artist 7", "/music/song7.mp3"));
		playlist.add(new Song(8,"Song 8", "Artist 8", "/music/song8.mp3"));
		playlist.add(new Song(9,"Song 9", "Artist 9", "/music/song9.mp3"));
		playlist.add(new Song(10,"Song 10", "Artist 10", "/music/song10.mp3"));
	}

	// 곡 순서 업데이트 로직
	public void updateSongOrder(List<Integer> orderedIds) {
		// 새로 받은 orderedIds 순서대로 플레이리스트를 재정렬
		playlist.sort((song1, song2) -> {
			int index1 = orderedIds.indexOf(song1.getId());
			int index2 = orderedIds.indexOf(song2.getId());
			return Integer.compare(index1, index2);
		});
	}

	public List<Song> getPlaylist() {
		return playlist;
	}

	public void addSong(Song song) {
		playlist.add(song);
	}



}