package com.example.myfridge;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfridge.Models.RecipeIDResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeHolder> {

    private ArrayList<RecipeIDResponse> recipeIDArray;
    private Context context;

    public RecipeRecyclerAdapter(Context context, ArrayList<RecipeIDResponse> recipeIDArray){
        this.recipeIDArray = recipeIDArray;
    }

    public class RecipeHolder extends RecyclerView.ViewHolder{
        TextView recipeName;
        ImageView recipeImage;
        TextView servings;
        TextView likes;
        TextView time;

        public RecipeHolder(final View view){
            super(view);
            recipeName = view.findViewById(R.id.recipeNameID);
            recipeImage = view.findViewById(R.id.recipeImageID);
            servings = view.findViewById(R.id.servingsTxtViewID);
            likes = view.findViewById(R.id.likesTxtViewID);
            time = view.findViewById(R.id.timeTxtViewID);

        }
    }

    @NonNull
    @Override
    public RecipeRecyclerAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_recipe, parent, false);
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeRecyclerAdapter.RecipeHolder holder, int position) {

        Log.i("--test--", String.valueOf(recipeIDArray.get(position).id));
        holder.recipeName.setText(String.valueOf(recipeIDArray.get(position).title));

        //holder.servings.setText(Integer.toString(recipeIDArray.get(position).getServingsNumber()) + " people");
        holder.likes.setText(Integer.toString(recipeIDArray.get(position).likes) + " likes");
        //holder.time.setText(Integer.toString(recipeArrayID.get(position).getTime()) + " min");
        Picasso.get().load(recipeIDArray.get(position).image).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return recipeIDArray.size();
    }
}
