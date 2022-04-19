package com.example.myfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfridge.Listeners.RecipeOnClickListener;
import com.example.myfridge.Models.FullRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeHolder> {

    private ArrayList<FullRecipeResponse> recipeIDArray;
    private Context context;
    RecipeOnClickListener listener;

    public RecipeRecyclerAdapter(Context context, ArrayList<FullRecipeResponse> recipeIDArray, RecipeOnClickListener listener){
        this.context = context;
        this.recipeIDArray = recipeIDArray;
        this.listener = listener;
    }

    public class RecipeHolder extends RecyclerView.ViewHolder{
        TextView recipeName;
        ImageView recipeImage;
        TextView servings;
        TextView likes;
        TextView time;
        CardView recipeContainer;

        public RecipeHolder(final View view){
            super(view);
            recipeName = view.findViewById(R.id.recipeNameID);
            recipeImage = view.findViewById(R.id.recipeImageID);
            servings = view.findViewById(R.id.servingsTxtViewID);
            likes = view.findViewById(R.id.likesTxtViewID);
            time = view.findViewById(R.id.timeTxtViewID);
            recipeContainer = view.findViewById(R.id.recipeContainer);
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
        position = holder.getAdapterPosition();
        holder.recipeName.setText(String.valueOf(recipeIDArray.get(position).title));
        holder.servings.setText(recipeIDArray.get(position).servings + " people");
        holder.likes.setText(recipeIDArray.get(position).aggregateLikes + " likes");
        holder.time.setText(recipeIDArray.get(position).readyInMinutes + " min");
        Picasso.get().load(recipeIDArray.get(position).image).into(holder.recipeImage);
        int finalPosition = position;
        holder.recipeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClicked(recipeIDArray.get(finalPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeIDArray.size();
    }
}
