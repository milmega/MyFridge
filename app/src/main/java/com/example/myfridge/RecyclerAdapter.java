package com.example.myfridge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        LinearLayout itemRow;

        public MyViewHolder(final View view){
            super(view);
            nametxt = view.findViewById(R.id.productNameID);
            expirytxt = view.findViewById(R.id.expiryDateID);
            quantitytxt = view.findViewById(R.id.quantityID);
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
        holder.nametxt.setText(name);
        holder.expirytxt.setText(expiryDate);
        holder.quantitytxt.setText(amount);

        holder.itemRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateProduct.class);
                intent.putExtra("id", String.valueOf(productsList.get(position).getId()));
                intent.putExtra("name", String.valueOf(productsList.get(position).getName()));
                intent.putExtra("category", String.valueOf(productsList.get(position).getCategory()));
                intent.putExtra("amount", String.valueOf(productsList.get(position).getAmount()));
                intent.putExtra("dateOfExpiry", String.valueOf(productsList.get(position).getDateOfExpiry()));
                intent.putExtra("unit", String.valueOf(productsList.get(position).getUnit()));
                activity.startActivityForResult(intent, 1);
                //activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}
