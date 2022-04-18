package com.example.myfridge;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfridge.Listeners.RecipeIDResponseListener;
import com.example.myfridge.Models.RecipeIDResponse;

import java.util.ArrayList;


public class RecipeFragment extends Fragment {

    ArrayList<Recipe> recipeArray;
    RecyclerView recyclerView;
    RecipeRequestManager manager;
    ProgressDialog dialog;
    RecipeRecyclerAdapter adapter;
    View mainView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe, container, false);
        mainView = view;
        recipeArray = new ArrayList<>();
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading...");

        //addRecipes();
        setManager();
        return view;
    }

    /*private void setAdapter(){
        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter((MainActivity)getContext(), recipeArray);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((MainActivity)getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }*/

    private void setManager(){
        manager = new RecipeRequestManager(getContext());
        manager.getRecipeId(listener);
        dialog.show();
    }

    private final RecipeIDResponseListener listener = new RecipeIDResponseListener() {
        @Override
        public void didFetch(ArrayList<RecipeIDResponse> response, String message) {
            Log.i("--test--", "tutaj" + response.size());
            dialog.dismiss();
            recyclerView = mainView.findViewById(R.id.recipeRecycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            adapter = new RecipeRecyclerAdapter(getContext(), response); //instead recipe array should be call from api like response.recipes
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void didError(String message) {
            Log.i("--test", "niepowodzenie");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        }
    };

    private void addRecipes() {
        recipeArray.add(new Recipe("Salad", 3, 23, 15));
        recipeArray.add(new Recipe("Chicken and potatoes", 4, 57, 40));
        recipeArray.add(new Recipe("Pizza", 4, 99, 60));
        recipeArray.add(new Recipe("Lasagna", 2, 33, 45));
    }
}