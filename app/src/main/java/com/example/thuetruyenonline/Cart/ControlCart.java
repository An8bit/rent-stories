package com.example.thuetruyenonline.Cart;

import java.io.Serializable;

public class ControlCart implements Serializable {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNameStory() {
        return nameStory;
    }

    public void setNameStory(String nameStory) {
        this.nameStory = nameStory;
    }


    public String getSongaythue() {
        return songaythue;
    }

    public void setSongaythue(String songaythue) {
        this.songaythue = songaythue;
    }

    String buyer;
    String idTruyen;
    String img;

    public ControlCart(String id,String buyer, String idTruyen, String img, String nameStory, String songaythue) {
        this.id=id;
        this.buyer = buyer;
        this.idTruyen = idTruyen;
        this.img = img;
        this.nameStory = nameStory;
        this.songaythue = songaythue;
    }

    String nameStory;
    String songaythue;

}
