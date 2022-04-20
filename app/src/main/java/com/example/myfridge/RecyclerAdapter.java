package com.example.myfridge;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<Product>productsList;
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
        private ImageView categoryIcon;
        LinearLayout itemRow;

        public MyViewHolder(final View view){
            super(view);
            nametxt = view.findViewById(R.id.productNameID);
            expirytxt = view.findViewById(R.id.expiryDateID);
            quantitytxt = view.findViewById(R.id.quantityID);
            unittxt = view.findViewById(R.id.unitID);
            deleteButton = view.findViewById(R.id.deleteButton);
            categoryIcon = view.findViewById(R.id.foodIcon);
            itemRow = view.findViewById(R.id.list_item);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        String name = productsList.get(pos).getName();
        String expiryDate = productsList.get(pos).getDateOfExpiry();
        String amount = productsList.get(pos).getAmount();
        String unit = productsList.get(pos).getUnit();
        holder.nametxt.setText(name);

        try{
            Date date = new Date();
            if(date.after(new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate))){
                holder.expirytxt.setText("EXPIRED ON: " + expiryDate);
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
                bundle.putString("id", String.valueOf(productsList.get(pos).getId()));
                bundle.putString("name", String.valueOf(productsList.get(pos).getName()));
                bundle.putString("category", String.valueOf(productsList.get(pos).getCategory()));
                bundle.putString("amount", String.valueOf(productsList.get(pos).getAmount()));
                bundle.putString("dateOfExpiry", String.valueOf(productsList.get(pos).getDateOfExpiry()));
                bundle.putString("unit", String.valueOf(productsList.get(pos).getUnit()));

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
                db.deleteData(productsList.get(pos).getId());
                ((MainActivity) activity).replaceFragment(new MyFridgeFragment());
            }
        });

        if(productsList.get(pos).getCategory().equals("Dairy"))
            Picasso.get().load(R.drawable.dairy_icon).into(holder.categoryIcon);
        else if(productsList.get(pos).getCategory().equals("Meat"))
            Picasso.get().load(R.drawable.meat_icon).into(holder.categoryIcon);
        else if(productsList.get(pos).getCategory().equals("Fruits"))
            Picasso.get().load(R.drawable.fruit_icon).into(holder.categoryIcon);
        else if(productsList.get(pos).getCategory().equals("Vegetables"))
            Picasso.get().load(R.drawable.vegetables_icon).into(holder.categoryIcon);
        else if(productsList.get(pos).getCategory().equals("Others"))
            Picasso.get().load(R.drawable.others_icon).into(holder.categoryIcon);

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}
