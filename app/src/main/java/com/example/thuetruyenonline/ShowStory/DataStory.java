package com.example.thuetruyenonline.ShowStory;

public class DataStory {
    String id;
    String noidung;
    public DataStory(String id, String noidung) {
        this.id = id;
        this.noidung = noidung;
    }
    public String getId() {
        return id;
    }
    public String getNoidung() {
        return noidung;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

}
