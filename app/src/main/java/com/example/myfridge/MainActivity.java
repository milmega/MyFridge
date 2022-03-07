package com.example.myfridge;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myfridge.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    /*
    * Features to implement:
    * 1. if expiry date past current date then present EXPIRED
    * 2. amount option presented next to amount on list
    * 3. Edit item in list by presenting new view with editable details and save option
    * 4. save product on app memory!!! ---DONE---
    * 5. delete item from fridge (I need to add id to product and remove it from db by id)
    * 6. scan barcode to add an item
    * 7. improve design
    * 8. load data from db every time user refreshes view (check first if no of items in array is different than in db???)
    * */

    private HashMap<String, ArrayList<Product>> productsByCategories = new HashMap<>();
    private ActivityMainBinding binding;
    private boolean initializeMap = false;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(initializeMap == false){
            productsByCategories.put("All", new ArrayList<Product>());
            productsByCategories.put("Dairy", new ArrayList<Product>());
            productsByCategories.put("Meat", new ArrayList<Product>());
            productsByCategories.put("Fruits", new ArrayList<Product>());
            productsByCategories.put("Vegetables", new ArrayList<Product>());
            productsByCategories.put("Others", new ArrayList<Product>());
            initializeMap = true;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDB = new DatabaseHelper(MainActivity.this);
        storeDataInArray();

        replaceFragment(new MyFridgeFragment());

        binding.bottomNavigationBar.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.myfridge:
                    replaceFragment(new MyFridgeFragment());
                    break;
                case R.id.recipes:
                    replaceFragment(new RecipeFragment());
                    break;
                case R.id.add_item:
                    replaceFragment(new AddProductFragment());
                    break;
                case R.id.shopping_list:
                    replaceFragment(new ShoppingListFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void storeDataInArray(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                String amount = cursor.getString(3);
                String unit = cursor.getString(4);
                String dateOfExpiry = cursor.getString(5);

                Product product = new Product(name, category, dateOfExpiry, amount, unit);
                addProduct(product);
            }
        }
    }

    public ArrayList<Product> getProductsList(String title){
        return productsByCategories.get(title);
    }

    public void addProduct(Product product){
        ArrayList<Product>allProducts = productsByCategories.get("All");
        ArrayList<Product>listByCategory = productsByCategories.get(product.getCategory());
        listByCategory.add(product);
        allProducts.add(product);
        productsByCategories.put(product.getCategory(), listByCategory);
        productsByCategories.put("All", allProducts);
    }
}