package com.example.thuetruyenonline;

import java.io.Serializable;

public class Story implements Serializable {
    public Story(String id, String image, String tacgia, String gioithieu, String namestory) {
        this.id = id;
        this.image = image;
        this.tacgia = tacgia;
        this.gioithieu = gioithieu;
        this.namestory = namestory;
    }

    String id;
    String image;
    String tacgia;
    String gioithieu;
    String namestory;

    public String getGioithieu() {
        return gioithieu;
    }

    public void setGioithieu(String gioithieu) {
        this.gioithieu = gioithieu;
    }

    public String getTacgia() {

        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNamestory() {
        return namestory;
    }

    public void setNamestory(String namestory) {
        this.namestory = namestory;
    }



}
