package com.example.myfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeHolder> {

    private ArrayList<Recipe> recipeArray;
    private Context context;

    public RecipeRecyclerAdapter(Context context, ArrayList<Recipe> recipeArray){
        this.recipeArray = recipeArray;
    }

    public class RecipeHolder extends RecyclerView.ViewHolder{
        TextView recipeName;

        public RecipeHolder(final View view){
            super(view);
            recipeName = view.findViewById(R.id.recipeNameID);
        }
    }

    @NonNull
    @Override
    public RecipeRecyclerAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recipe, parent, false);
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeRecyclerAdapter.RecipeHolder holder, int position) {
        String name = recipeArray.get(position).getName();
        holder.recipeName.setText(name);
    }

    @Override
    public int getItemCount() {
        return recipeArray.size();
    }
}
