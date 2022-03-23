package com.example.myfridge;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;

@SuppressWarnings("deprecation")
public class Sample extends Thread{
    private Context context;
    private Activity activity;
    private String barcodeNumber;
    private boolean exit;

    public class Product {
        public String barcode_number;
        public String barcode_formats;
        public String mpn;
        public String model;
        public String asin;
        public String title;
        public String category;
        public String manufacturer;
        public String brand;
        public String[] contributors;
        public String age_group;
        public String ingredients;
        public String nutrition_facts;
        public String color;
        public String format;
        public String multipack;
        public String size;
        public String length;
        public String width;
        public String height;
        public String weight;
        public String release_date;
        public String description;
        public Object[] features;
        public String[] images;
    }

    public class RootObject {
        public Product[] products;
    }

    public Sample(String barcodeNumber, Context context, Activity activity){
        this.context = context;
        this.barcodeNumber = barcodeNumber;
        this.activity = activity;
        exit = false;
    }

    @Override
    public void run() {
        Looper.prepare();
        while(!exit){
            ((MainActivity)activity).showToast("in thread");
            try  {
                convertBarcodeToProduct(barcodeNumber);
                Log.i("--test--", "inrun");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("--test--", e.toString());
            }
            stopThread();
        }
    }

    public void stopThread(){
        exit = true;
    }


    public void convertBarcodeToProduct(String barcodeNumber){
        try {
            Log.i("--test--", "b"+barcodeNumber);
            URL url = new URL("https://api.barcodelookup.com/v3/products?barcode="+barcodeNumber+"&formatted=y&key=hup7wyqk76w7djem4qln47niugopuz");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = "";
            String data = "";

            while (null != (str= br.readLine())) {
                data+=str;
            }

            Gson g = new Gson();
            RootObject value = g.fromJson(data, RootObject.class);

            String name = value.products[0].title;
            String cat = value.products[0].category;
            String weight = value.products[0].weight;
            Log.i("--test--", "DONE: " + name + ", " + cat + ", " + weight);
            ((MainActivity)activity).showToast("DONE");
            ((MainActivity)activity).fillFormUsingJSON(name, cat, weight);

        } catch (FileNotFoundException e){
            Log.i("--test--", e.toString());
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    ((MainActivity)activity).showToast("Product not found! Try to insert it manually.");
                }
            });
        }
        catch (Exception ex) {
            Log.i("--test--", ex.toString());
            ex.printStackTrace();
        }
    }
}
