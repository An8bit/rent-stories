package com.example.thuetruyenonline.pagehome;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Story implements Serializable {
    public Story(String id, String image, String tacgia, String gioithieu, String namestory,String theloai,String noidung,String gia) {
        this.id = id;
        this.image = image;
        this.tacgia = tacgia;
        this.gioithieu = gioithieu;
        this.namestory = namestory;
        this.theloai=theloai;
        this.noidung=noidung;
        this.gia=gia;


    }
    String gia;

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    String noidung;

    String id;
     String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String tacgia;
    String gioithieu;
    String namestory;
    String theloai;

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

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


    public String getNamestory() {
        return namestory;
    }

    public void setNamestory(String namestory) {
        this.namestory = namestory;
    }



}
