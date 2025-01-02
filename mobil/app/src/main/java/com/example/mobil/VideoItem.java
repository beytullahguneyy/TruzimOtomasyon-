package com.example.mobil;

public class VideoItem {
    private String title;
    private String description;
    private String videoId;
    private String youtubeId;
    private String longDesc;

    public VideoItem(String title, String description, String videoId) {
        this.title = title;
        this.description = description;
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }
}