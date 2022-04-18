package com.example.myfridge;

import android.content.Context;

import com.example.myfridge.Listeners.RecipeIDResponseListener;
import com.example.myfridge.Models.RecipeIDResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RecipeRequestManager {
    //extends as a thread
    //run a thread
    //retrieve json data
    //convert it to recipe object
    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com").addConverterFactory(GsonConverterFactory.create()).build();

    public RecipeRequestManager(Context context){
        this.context = context;
    }

    public void getRecipeId(RecipeIDResponseListener listener){
        CallRecipeByIngredients callRecipe = retrofit.create(CallRecipeByIngredients.class);
        Call<ArrayList<RecipeIDResponse>> call = callRecipe.callRecipeId(context.getString(R.string.api_key), "chicken,potatoes,pasta,milk,cheese",
                "10", true, "1", true);
        call.enqueue(new Callback<ArrayList<RecipeIDResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeIDResponse>> call, Response<ArrayList<RecipeIDResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeIDResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }

    private interface CallRecipeByIngredients{
        @GET("recipes/findByIngredients")
        Call<ArrayList<RecipeIDResponse>> callRecipeId(
                @Query("apiKey") String apiKey,
                @Query("ingredients") String ingredients,
                @Query("number") String number,
                @Query("limitLicense") Boolean limitLicense,
                @Query("ranking") String ranking,
                @Query("ignorePantry") Boolean ignorePantry


        );
    }

}
