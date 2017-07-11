package com.example.android.whereareyougo.ui.data.database.entity;

/**
 * Created by nguyenanhtrung on 11/07/2017.
 */

public class VenueCategory {
    private String id;
    private String categoryName;
    private int categoryIconId;
    private boolean isChoose;

    public VenueCategory(String id, String categoryName, int categoryIconId) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryIconId = categoryIconId;
    }

    public VenueCategory() {
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryIconId() {
        return categoryIconId;
    }

    public void setCategoryIconId(int categoryIconId) {
        this.categoryIconId = categoryIconId;
    }
}
