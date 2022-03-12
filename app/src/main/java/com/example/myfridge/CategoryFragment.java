package com.example.myfridge;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;


public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private String title;

    public CategoryFragment(String title){
        this.title = title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        setRecyclerAdapter();
    }



    public void setRecyclerAdapter(){
        RecyclerAdapter adapter = new RecyclerAdapter((MainActivity)getActivity(), (MainActivity)getContext(), ((MainActivity) getActivity()).getProductsList(title)); //pass good array
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((MainActivity)getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}