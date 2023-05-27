package com.example.navigationdrawer;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class DressModel {
    private int ID;
    private String name;
    Bitmap image;

    private String description;
    private int price;
    private String size;
    private String phoneNo;
    private String city;

    public DressModel(int ID, String name, Bitmap image, String description, int price, String size, String phoneNo, String city) {
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.size = size;
        this.phoneNo = phoneNo;
        this.city = city;
    }

    public DressModel(int ID){
this.ID = ID;
    }


//    public DressModel(int ID, String name, Bitmap image){
//        this.ID = ID;
//        this.name = name;
//        this.image = image;
//
//    }

    public Bitmap getImage() {
        return image;
    }

    public int getID() {
        return ID;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getCity() {
        return city;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "customerModel{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}

