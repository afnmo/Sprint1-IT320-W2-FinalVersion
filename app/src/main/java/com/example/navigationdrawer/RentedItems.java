package com.example.navigationdrawer;

import java.util.Date;

public class RentedItems {
    private int id;
    private int days;
    private String occDate;
    private String pickDate;
    private String name;
    private String phone;
    private int itemID;
    private String currentUser;

    public RentedItems(int id, int days, String occDate, String pickDate, String name, String phone) {
        this.id = id;
        this.days = days;
        this.occDate = occDate;
        this.pickDate = pickDate;
        this.name = name;
        this.phone = phone;
    }

    public int getDays() {
        return days;
    }

    public String getOccDate() {
        return occDate;
    }

    public String getPickDate() {
        return pickDate;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setOccDate(String occDate) {
        this.occDate = occDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
