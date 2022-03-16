package com.example.myfridge;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<Product>productsList;
    int position;
    Context context;
    Activity activity;

    public RecyclerAdapter(Activity activity, Context context, ArrayList<Product> productsList){
        this.context = context;
        this.activity = activity;
        this.productsList = productsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nametxt;
        private TextView expirytxt;
        private TextView quantitytxt;
        private TextView unittxt;
        private ImageButton deleteButton;
        LinearLayout itemRow;

        public MyViewHolder(final View view){
            super(view);
            nametxt = view.findViewById(R.id.productNameID);
            expirytxt = view.findViewById(R.id.expiryDateID);
            quantitytxt = view.findViewById(R.id.quantityID);
            unittxt = view.findViewById(R.id.unitID);
            deleteButton = view.findViewById(R.id.deleteButton);
            itemRow = view.findViewById(R.id.list_item);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        this.position = position;
        String name = productsList.get(position).getName();
        String expiryDate = productsList.get(position).getDateOfExpiry();
        String amount = productsList.get(position).getAmount();
        String unit = productsList.get(position).getUnit();
        holder.nametxt.setText(name);

        try{
            Date date = new Date();
            if(date.after(new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate))){
                holder.expirytxt.setText("Expired on: " + expiryDate);
                holder.expirytxt.setTypeface(null, Typeface.BOLD);
            }
            else{
                holder.expirytxt.setText("Expiry: " + expiryDate);
            }
        }catch(Exception e){}

        holder.quantitytxt.setText(amount);
        holder.unittxt.setText(unit);

        holder.itemRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(productsList.get(position).getId()));
                bundle.putString("name", String.valueOf(productsList.get(position).getName()));
                bundle.putString("category", String.valueOf(productsList.get(position).getCategory()));
                bundle.putString("amount", String.valueOf(productsList.get(position).getAmount()));
                bundle.putString("dateOfExpiry", String.valueOf(productsList.get(position).getDateOfExpiry()));
                bundle.putString("unit", String.valueOf(productsList.get(position).getUnit()));

                AppCompatActivity appActivity = (AppCompatActivity)v.getContext();
                UpdateFragment updateFragment = new UpdateFragment();
                updateFragment.setArguments(bundle);
                appActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, updateFragment).addToBackStack(null).commit();
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deleteData(productsList.get(position).getId());

                ((MainActivity) activity).replaceFragment(new MyFridgeFragment());

            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}
