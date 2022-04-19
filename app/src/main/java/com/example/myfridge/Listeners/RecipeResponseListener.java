package com.example.myfridge.Listeners;

import com.example.myfridge.Models.FullRecipeResponse;

public interface RecipeResponseListener {
    void didFetch(FullRecipeResponse recipe, String message);
    void didError(String message);
}
