package com.example.myfridge;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private ArrayAdapter<CharSequence>adapter;

    TextView productName;
    TextView category;
    EditText expiryDate;
    Button submitButton;
    EditText amountEditTxt;
    RadioGroup amountUnits;
    RadioButton checkedButton;

    //DatePickerDialog.OnDateSetListener setListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        //regarding name edit text
        productName = view.findViewById(R.id.nameOfAddedProduct);

        //regarding category spinner
        spinner = view.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getContext(),R.array.categories, R.layout.spinner_item);//, android.R.layout.simple_spinner_item); //get context or get activity
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //regarding amount
        amountUnits = view.findViewById(R.id.radioGroup);
        amountEditTxt = view.findViewById(R.id.amountEditTxt);

        //regarding expiry date calendar
        expiryDate = view.findViewById(R.id.editTextDate);
        expiryDate.setInputType(InputType.TYPE_NULL);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        expiryDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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

        //regarding submit button
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name  = productName.getText().toString();
                String cat = spinner.getSelectedItem().toString();
                String amount = amountEditTxt.getText().toString();
                int radioButtonId = amountUnits.getCheckedRadioButtonId();
                checkedButton = view.findViewById(radioButtonId);
                String unit = checkedButton.getText().toString();
                String date = expiryDate.getText().toString();

                if(name.equals("") || cat.equals("--------") || amount.equals("") || unit.equals("") || date.equals("")){
                    Toast toast = Toast.makeText(getContext(), "Enter all values", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    //create and add product and clear all values or redirect to main view;
                    Product product = new Product(name, cat, date, amount, unit);
                    ((MainActivity) getActivity()).addProduct(product);

                    DatabaseHelper myDB = new DatabaseHelper(getContext());
                    myDB.addProduct(name, cat, amount,  unit, date);

                }

            }
        });


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //regarding spinner adapter. DO i need it?
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}