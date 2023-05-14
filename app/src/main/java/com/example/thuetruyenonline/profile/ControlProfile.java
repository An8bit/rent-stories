package com.example.thuetruyenonline.profile;

import java.io.Serializable;

public class  ControlProfile implements Serializable {
    String buyer;
    String namestory;
    String idtruyen;

    public String getNgayhethan() {
        return ngayhethan;
    }

    public void setNgayhethan(String ngayhethan) {
        this.ngayhethan = ngayhethan;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    String gmail;

    String ngayhethan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    String noidung;

    public ControlProfile(String gmail,String id, String namestory, String idtruyen, String img, String songaythue,String noidung,String ngayhethan) {
        this.id=id;
        this.namestory = namestory;
        this.idtruyen = idtruyen;
        this.img = img;
        this.songaythue = songaythue;
        this.noidung=noidung;
        this.ngayhethan=ngayhethan;
        this.gmail=gmail;
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
