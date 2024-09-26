package kr.co.duck.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "response")
public class ApiResponse {
    private List<Song> items;

    @XmlElement(name = "item")
    public List<Song> getItems() {
        return items;
    }

    public void setItems(List<Song> items) {
        this.items = items;
    }
}

class Song {
    private String title;
    private String link;
    private String albumTitle;
    private String albumImage;
    private String albumArtist;
    private String description;
    private String runningTime;
    private String albumRelease;

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @XmlElement(name = "album")
    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    @XmlElement(name = "image")
    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    @XmlElement(name = "maniadb:artist")
    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "runningtime")
    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    @XmlElement(name = "albumRelease")
    public String getAlbumRelease() {
        return albumRelease;
    }

    public void setAlbumRelease(String albumRelease) {
        this.albumRelease = albumRelease;
    }
}
