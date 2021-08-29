package com.kamanov.game;

import android.graphics.Bitmap;

public class User {
    String name;
    String age;
    //String image;
    Integer level;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public User(String name, String age, String image, Integer level) {
        this.name = name;
        this.age = age;
        //this.image = image;
        this.level = level;
    }


}
