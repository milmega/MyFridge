package com.example.myfridge.Listeners;

import com.example.myfridge.Models.RecipeIDResponse;

import java.util.ArrayList;

public interface RecipeIDResponseListener {
    void didFetch(ArrayList<RecipeIDResponse> response, String message);
    void didError(String message);
}
