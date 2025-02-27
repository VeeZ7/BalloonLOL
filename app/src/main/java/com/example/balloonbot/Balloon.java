package com.example.balloonbot;

public class Balloon {

    private String name;
    private double price;
    private int quantity;
    private int imgResourceID;
    private boolean isSelected;


    public Balloon(String name, double price, int imgResourceID) {
        this.name = name;
        this.price = price;
        this.imgResourceID = imgResourceID;
        this.quantity = 0;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
        this.isSelected = true;
    }
    public int getImgResourceID() {
        return imgResourceID;

    }

    public void setImgResourceID(int imgResourceID) {
        this.imgResourceID = imgResourceID;
    }

    public boolean isSelected() {
        return isSelected;

    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }















}
