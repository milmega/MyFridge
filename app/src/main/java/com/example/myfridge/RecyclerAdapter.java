package com.example.myfridge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<Product>productsList;

    public RecyclerAdapter(ArrayList<Product> productsList){
        this.productsList = productsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nametxt;
        private TextView expirytxt;
        private TextView quantitytxt;

        public MyViewHolder(final View view){
            super(view);
            nametxt = view.findViewById(R.id.productNameID);
            expirytxt = view.findViewById(R.id.expiryDateID);
            quantitytxt = view.findViewById(R.id.quantityID);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String name = productsList.get(position).getName();
        String expiryDate = productsList.get(position).getDateOfExpiry();
        String amount = productsList.get(position).getAmount();
        holder.nametxt.setText(name);
        holder.expirytxt.setText(expiryDate);
        holder.quantitytxt.setText(amount );
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}
