package com.example.finalproject;

import java.util.List;

public class Product {
    public String l_id;
    private String name;
    private  String introduction;
    private  String time;
    private  String calorie;
    private  String capacity;
    private  String difficulty;
    private  String image_link;

    public String getProductId() {
        return l_id;
    }
    public void setProductId(String productId) {
        this.l_id = productId;
    }
    public String getPname() {
        return name;
    }
    public void setPname(String pname) {
        this.name = pname;
    }

    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String gettime() {
        return time;
    }
    public void settime(String time) {
        this.time = time;
    }
    public String getcalorie() {
        return calorie;
    }
    public void setcalorie(String calorie) {
        this.calorie = calorie;
    }
    public String getcapacity() {
        return capacity;
    }
    public void setcapacity(String capacity) {
        this.capacity = capacity;
    }
    public String getdifficulty() {
        return difficulty;
    }
    public void setdifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public String getimage_link() {
        return image_link;
    }
    public void setimage_link(String image_link) {
        this.image_link = image_link;
    }



}
