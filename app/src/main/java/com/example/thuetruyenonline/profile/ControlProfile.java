package com.example.thuetruyenonline.profile;

import java.io.Serializable;

public class  ControlProfile implements Serializable {
    String buyer;
    String namestory;
    String idtruyen;

    public ControlProfile( String namestory, String idtruyen, String img, String songaythue) {
        this.namestory = namestory;
        this.idtruyen = idtruyen;
        this.img = img;
        this.songaythue = songaythue;
    }

    String img;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getNamestory() {
        return namestory;
    }

    public void setNamestory(String namestory) {
        this.namestory = namestory;
    }

    public String getIdtruyen() {
        return idtruyen;
    }

    public void setIdtruyen(String idtruyen) {
        this.idtruyen = idtruyen;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSongaythue() {
        return songaythue;
    }

    public void setSongaythue(String songaythue) {
        this.songaythue = songaythue;
    }

    String songaythue;
}