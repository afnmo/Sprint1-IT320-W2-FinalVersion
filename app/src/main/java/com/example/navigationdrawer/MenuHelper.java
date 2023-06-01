package com.example.navigationdrawer;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;

import androidx.annotation.NonNull;

public class MenuHelper implements Parcelable {
    private Menu menu;

    public MenuHelper() {
    }

    public MenuHelper(Menu menu) {
        this.menu = menu;
    }

    protected MenuHelper(Parcel in) {
    }

    public static final Creator<MenuHelper> CREATOR = new Creator<MenuHelper>() {
        @Override
        public MenuHelper createFromParcel(Parcel in) {
            return new MenuHelper(in);
        }

        @Override
        public MenuHelper[] newArray(int size) {
            return new MenuHelper[size];
        }
    };

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }
}
