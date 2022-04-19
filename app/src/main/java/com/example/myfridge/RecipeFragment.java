package com.example.myfridge;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfridge.Listeners.RecipeIDResponseListener;
import com.example.myfridge.Listeners.RecipeOnClickListener;
import com.example.myfridge.Listeners.RecipeResponseListener;
import com.example.myfridge.Models.ExtendedIngredient;
import com.example.myfridge.Models.FullRecipeResponse;
import com.example.myfridge.Models.RecipeIDResponse;

import java.util.ArrayList;


public class RecipeFragment extends Fragment {

    RecyclerView recyclerView;
    RequestManager manager;
    ProgressDialog dialog;
    RecipeRecyclerAdapter adapter;
    View mainView;
    ArrayList<FullRecipeResponse>recipes;
    int responseSize = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe, container, false);
        mainView = view;

        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading...");
        recipes = new ArrayList<>();

        //addRecipes();
        setIDManager();
        return view;
    }

    private void setIDManager(){
        manager = new RequestManager(getContext());
        manager.getRecipeId(idListener);
        dialog.show();
    }

    private void setRecipeManager(int id){
        manager.getRecipeByID(recipeListener, id);
    }

    private final RecipeIDResponseListener idListener = new RecipeIDResponseListener() {
        @Override
        public void didFetch(ArrayList<RecipeIDResponse> response, String message) {
            dialog.dismiss();
            recyclerView = mainView.findViewById(R.id.recipeRecycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            responseSize = response.size();

            for(int i = 0; i < response.size(); i++){
                setRecipeManager(response.get(i).id);
            }
        }

        @Override
        public void didError(String message) {
            dialog.dismiss();
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeResponseListener recipeListener = new RecipeResponseListener(){

        @Override
        public void didFetch(FullRecipeResponse recipe, String message) {
            dialog.dismiss();
            recipes.add(recipe);
            if(responseSize == recipes.size()){
                adapter = new RecipeRecyclerAdapter(getContext(), recipes, onCLicklistener); //instead recipe array should be call from api like response.recipes
                recyclerView.setAdapter(adapter);
            }
        }

        @Override
        public void didError(String message) {
            dialog.dismiss();
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeOnClickListener onCLicklistener = new RecipeOnClickListener(){
        @Override
        public void onRecipeClicked(FullRecipeResponse recipe) {
            String ingredients = "";
            for(int i = 0; i < recipe.extendedIngredients.size(); i++){
                ExtendedIngredient ing = recipe.extendedIngredients.get(i);
                ingredients += "- "+ ing.name + " " + ing.amount + " " + ing.measures.metric.unitShort + "\n";
            }
            ArrayList<String> instruction = new ArrayList<>();
            for(int i = 0; i < recipe.analyzedInstructions.size(); i++){
                //Log.i("--test--", "krok: " + recipe.analyzedInstructions.get(i));
                for(int j = 0; j < recipe.analyzedInstructions.get(i).steps.size(); j++){
                    //Log.i("--test--", "krok " + j + " - " + recipe.analyzedInstructions.get(i).steps.get(j).step);
                    instruction.add(recipe.analyzedInstructions.get(i).steps.get(j).step);
                }
            }

            Bundle bundle = new Bundle();
            bundle.putString("title",recipe.title);
            bundle.putString("image",recipe.image);
            bundle.putString("ingredients", ingredients);
            bundle.putString("servings", String.valueOf(recipe.servings));
            bundle.putString("time", String.valueOf(recipe.readyInMinutes));
            bundle.putString("likes", String.valueOf(recipe.aggregateLikes));
            bundle.putStringArrayList("instruction", instruction);

            AppCompatActivity appActivity = (AppCompatActivity) getContext();
            RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
            recipeDetailsFragment.setArguments(bundle);
            appActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, recipeDetailsFragment).addToBackStack(null).commit();


        }
    };
}