package com.example.myfridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecipeFragment extends Fragment {

    ArrayList<Recipe> recipeArray;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe, container, false);
        recipeArray = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recipeRecycler);
        addRecipes();
        setAdapter();
        return view;
    }

    private void setAdapter(){
        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter((MainActivity)getContext(), recipeArray);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((MainActivity)getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void addRecipes() {
        recipeArray.add(new Recipe("Salad"));
        recipeArray.add(new Recipe("Chicken and potatoes"));
        recipeArray.add(new Recipe("Pizza"));
        recipeArray.add(new Recipe("Lasagna"));
    }
}