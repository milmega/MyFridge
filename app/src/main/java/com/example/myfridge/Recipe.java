package com.example.myfridge;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String photo;
    private String description;
    private ArrayList<Product>products;
    private int servingsNumber;
    private int likes;
    private int time;

    public Recipe(String name, int servingsNumber, int likes, int time){
        this.name = name;
        this.servingsNumber = servingsNumber;
        this.likes = likes;
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setServingsNumber(int servingsNumber) {
        this.servingsNumber = servingsNumber;
    }

    public int getServingsNumber() {
        return servingsNumber;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
