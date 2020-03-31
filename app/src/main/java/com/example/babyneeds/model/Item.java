package com.example.babyneeds.model;

public class Item {
    private int id;
    private String itemName;
    private int itemQuantity;
    private String itemColor;
    private int itemSize;
    private String dateItemAdded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }

    public Item() {
        this.itemName = itemName;
    }

    public Item(String itemName, int itemQuantity, String itemColor, int itemSize) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemColor = itemColor;
        this.itemSize = itemSize;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

}
