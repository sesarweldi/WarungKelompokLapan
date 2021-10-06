package com.warung88.Models;

public class Food {
    final private  int NO_IMAGE = -1;
    private  String foodName;
    public void setType(String type) {
        this.type = type;
    }
    private String type;
    private int mImageResouce = NO_IMAGE;
    private  int foodPrice;

    public int getNO_IMAGE() {
        return NO_IMAGE;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getType() {
        return type;
    }

    public int getmImageResouce() {
        return mImageResouce;
    }

    public void setmImageResouce(int mImageResouce) {
        this.mImageResouce = mImageResouce;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public Food(String foodName, String type, int mImageResouce, int foodPrice) {
        this.foodName = foodName;
        this.type = type;
        this.mImageResouce = mImageResouce;
        this.foodPrice = foodPrice;
    }
}
