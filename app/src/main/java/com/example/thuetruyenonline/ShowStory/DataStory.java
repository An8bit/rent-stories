package com.example.thuetruyenonline.ShowStory;

public class DataStory {
    String chapter;
    String id;
    String noidung;

    public DataStory(String chapter, String id, String noidung) {
        this.chapter = chapter;
        this.id = id;
        this.noidung = noidung;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
