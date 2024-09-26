package kr.co.duck.beans;

public class Song {
    private int id;  // 새로운 ID 필드
    private String title;
    private String artist;
    private String url;

    public Song(int id, String title, String artist, String url) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.url = url;
    }

    // 기본 생성자도 필요할 수 있음
    public Song() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
