package com.example.myfridge;

import android.graphics.drawable.Icon;
import android.media.Image;

import java.util.Date;

public class Product {
    private String name;
    private String category;
    private String amount;
    private String dateOfExpiry;
    private String unit; //representing quantity in kg, g, or pc.
    private int barcode;
    private Icon icon;

    public Product(String name, String category, String dateOfExpiry, String amount, String unit){
        this.name = name;
        this.category = category;
        this.dateOfExpiry = dateOfExpiry;
        this.amount = amount;
        this.unit = unit;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return category;
    }
    public void setDateOfExpiry(String dateOfExpiry){
        this.dateOfExpiry = dateOfExpiry;
    }
    public String getDateOfExpiry(){
        return dateOfExpiry;
    }
    public void setAmount(String amount){
        this.amount = amount;
    }
    public String getAmount(){
        return amount;
    }
    public void setBarcode(int barcode){
        this.barcode = barcode;
    }
    public int getBarcode(){
        return barcode;
    }
    public void setIcon(Icon icon){
        this.icon = icon;
    }
    public Icon getIcon(){
        return icon;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
