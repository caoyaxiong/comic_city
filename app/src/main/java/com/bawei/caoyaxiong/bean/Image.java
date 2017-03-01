package com.bawei.caoyaxiong.bean;

/**
 * Created by dell on 2017/1/13.
 */
public class Image {
    private String imageUrl;
    private int id;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageUrl='" + imageUrl + '\'' +
                ", id=" + id +
                '}';
    }
}
