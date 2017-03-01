package com.bawei.caoyaxiong.bean;

/**
 * Created by dell on 2017/1/12.
 */
public class TypeGrid {
    private String name;
    private String type;
    private String area;
    private String des;
    private String  coverImg;

    public TypeGrid(String name, String type, String area, String des, String coverImg) {
        this.name = name;
        this.type = type;
        this.area = area;
        this.des = des;
        this.coverImg = coverImg;
    }

    public TypeGrid() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    @Override
    public String toString() {
        return "TypeGrid{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", area='" + area + '\'' +
                ", des='" + des + '\'' +
                ", coverImg=" + coverImg +
                '}';
    }
}
