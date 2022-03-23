package com.example.myfridge;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myfridge.databinding.ActivityMainBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    /*
    * Features to implement:
    * 1. if expiry date past current date then present EXPIRED change it to expire after today
    * 2. amount option presented next to amount on list --DONE--
    * 3. Edit item in list by presenting new view with editable details and save option --DONE--
    * 4. save product on app memory!!! ---DONE---
    * 5. delete item from fridge (I need to add id to product and remove it from db by id) --DONE--
    * 6. scan barcode to add an item
    * 7. improve design
    * 8. load data from db every time user refreshes view (check first if no of items in array is different than in db???) --DONE--
    * 9. Make data in a row to be one liner. CHaracter limits? --DONE--
    * 10. View lock orientation - ONLY VERTICAL --DONE--
    * */

    private HashMap<String, ArrayList<Product>> productsByCategories = new HashMap<>();
    private ActivityMainBinding binding;
    DatabaseHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDB = new DatabaseHelper(MainActivity.this);
        replaceFragment(new MyFridgeFragment());

        binding.bottomNavigationBar.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.myfridge:
                    storeDataInArray();
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

    public void showToast(String message){
        Log.i("--test--","+"+message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void replaceFragment(Fragment fragment){
        storeDataInArray(); //whenever fragment is changed, data is updated
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void storeDataInArray(){
        productsByCategories.put("All", new ArrayList<Product>());
        productsByCategories.put("Dairy", new ArrayList<Product>());
        productsByCategories.put("Meat", new ArrayList<Product>());
        productsByCategories.put("Fruits", new ArrayList<Product>());
        productsByCategories.put("Vegetables", new ArrayList<Product>());
        productsByCategories.put("Others", new ArrayList<Product>());

        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                String amount = cursor.getString(3);
                String unit = cursor.getString(4);
                String dateOfExpiry = cursor.getString(5);

                Product product = new Product(id, name, category, dateOfExpiry, amount, unit);
                addProduct(product);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        replaceFragment(new MyFridgeFragment());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentResult.getContents() != null){
            Log.i("--test--", intentResult.getContents());
            Sample sample = new Sample(intentResult.getContents(), this.getApplicationContext(), this);
            sample.start();
        }
        else {
            Toast.makeText(this.getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
        }
    }

    public void fillFormUsingJSON(String name, String category, String weight){
        AddProductFragment updated = new AddProductFragment();
        updated.setInfo(name, category, weight);
        replaceFragment(updated);
    }
}