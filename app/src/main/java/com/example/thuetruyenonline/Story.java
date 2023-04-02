package com.example.thuetruyenonline;

public class Story {
    String id;
    String image;

    public Story(String id, String image, String namestory) {
        this.id = id;
        this.image = image;
        this.namestory = namestory;
    }

    String namestory;

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
