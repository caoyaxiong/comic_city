package com.bawei.caoyaxiong.bean;

/**
 * Created by dell on 2017/1/13.
 */
public class Chapters {
    private String name;
    private String id;

    public Chapters() {
    }

    public Chapters(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Chapters{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
