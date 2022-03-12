package com.example.myfridge;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateProduct extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    Context context;

    TextView productName;
    //TextView category;
    EditText expiryDate;
    Button updateButton;
    EditText amountEditTxt;
    RadioGroup amountUnits;
    //RadioButton checkedButton;

    String id, name, category, amount, unit, dateOfExpiry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        context = getApplicationContext();


        productName = findViewById(R.id.nameOfAddedProductUpdateView);

        //regarding amount
        amountEditTxt = findViewById(R.id.amountEditTxtUpdateView);
        amountUnits = findViewById(R.id.radioGroupUpdateView);
        //checkedButton = findViewById(amountUnits.getCheckedRadioButtonId());

        //regarding category spinner
        spinner = findViewById(R.id.spinnerUpdateView);
        adapter = ArrayAdapter.createFromResource(this.getApplicationContext(),R.array.categories, R.layout.spinner_item);//, android.R.layout.simple_spinner_item); //get context or get activity
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //regarding calendar
        expiryDate = findViewById(R.id.editTextDateUpdateView);
        expiryDate.setInputType(InputType.TYPE_NULL);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        expiryDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateProduct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;
                        String date = day+"/"+month+"/"+year;
                        expiryDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        getAndSetIntentData();

        //regarding updatebutton
        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name  = productName.getText().toString();
                category = spinner.getSelectedItem().toString();
                amount = amountEditTxt.getText().toString();
                int radioButtonId = amountUnits.getCheckedRadioButtonId();
                RadioButton checkedButton = findViewById(radioButtonId);
                unit = checkedButton.getText().toString();
                dateOfExpiry = expiryDate.getText().toString();

                if(name.equals("") || category.equals("--------") || amount.equals("") || unit.equals("") || dateOfExpiry.equals("")){
                    Toast toast = Toast.makeText(UpdateProduct.this, "Enter all values", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    //create and add product and clear all values or redirect to main viev
                    DatabaseHelper db = new DatabaseHelper(UpdateProduct.this);
                    db.updateData(id, name, category, amount, unit, dateOfExpiry);
                    //finish();
                    //Intent intent = new Intent(UpdateProduct.this, MainActivity.class);
                     //startActivity(intent);
                }
            }
        });
    }

    void getAndSetIntentData(){
        //get data
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        category = getIntent().getStringExtra("category");
        amount = getIntent().getStringExtra("amount");
        unit = getIntent().getStringExtra("unit");
        dateOfExpiry = getIntent().getStringExtra("dateOfExpiry");

        //set data
        productName.setText(name);
        amountEditTxt.setText(amount);
        expiryDate.setText(dateOfExpiry);
        spinner.setSelection(((ArrayAdapter) spinner.getAdapter()).getPosition(category));
        int idx = 0;
        if(unit.equals("g"))
            idx = 1;
        else if(unit.equals("pc."))
            idx = 2;
        ((RadioButton) amountUnits.getChildAt(idx)).setChecked(true);
    }
}