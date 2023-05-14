package com.example.navigationdrawer;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class DressModel {
    private int ID;
    private String name;
    Bitmap image;


    public DressModel(int ID){
this.ID = ID;
    }


    public DressModel(int ID, String name, Bitmap image){
        this.ID = ID;
        this.name = name;
        this.image = image;

    }

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


    @Override
    public String toString() {
        return "customerModel{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}

