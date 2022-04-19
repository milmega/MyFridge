package com.example.myfridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeDetailsFragment extends Fragment {

    TextView name;
    TextView ingredients;
    ImageView image;
    TextView servings;
    TextView likes;
    TextView time;
    RecyclerView instructionRecyclerView;
    InstructionRecyclerAdapter adapter;
    ArrayList<String> instructionSteps;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ((MainActivity)getActivity()).replaceFragment(new RecipeFragment());
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        name = view.findViewById(R.id.recipeNameDetails);
        //instruction = view.findViewById(R.id.instructionDetails);
        image = view.findViewById(R.id.imageDetails);
        servings = view.findViewById(R.id.servingsDetails);
        likes = view.findViewById(R.id.likesDetails);
        time = view.findViewById(R.id.timeDetails);
        ingredients = view.findViewById(R.id.ingredientsDetails);

        getAndSetIntentBundle();
        instructionRecyclerView = view.findViewById(R.id.instructionRecycler);
        instructionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        instructionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        instructionRecyclerView.setHasFixedSize(true);
        adapter = new InstructionRecyclerAdapter(getContext(), instructionSteps);
        instructionRecyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }

    private  void getAndSetIntentBundle(){
        Bundle bundle = this.getArguments();
        if(bundle != null){
            name.setText(bundle.getString("title"));
            servings.setText(bundle.getString("servings") + " people");
            likes.setText(bundle.getString("likes") + " likes");
            time.setText(bundle.getString("time") + " min");
            ingredients.setText(bundle.getString("ingredients"));
            instructionSteps = bundle.getStringArrayList("instruction");
            Picasso.get().load(bundle.getString("image")).into(image);
        }
    }
}