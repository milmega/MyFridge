package com.example.myfridge;

import android.app.Activity;
import android.content.Context;

import com.example.myfridge.Listeners.RecipeIDResponseListener;
import com.example.myfridge.Listeners.RecipeResponseListener;
import com.example.myfridge.Models.FullRecipeResponse;
import com.example.myfridge.Models.RecipeIDResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Activity activity;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com").addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    private String getIngredients(){
        ArrayList<Product>products = ((MainActivity) activity).getProductsList("All");
        String ingredients = "";

        for(int i = 0; i < products.size(); i++){
            ingredients += products.get(i).getName() + ",";
        }
        return  ingredients;
    }

    public void getRecipeId(RecipeIDResponseListener listener){
        CallRecipeByIngredients callRecipe = retrofit.create(RequestManager.CallRecipeByIngredients.class);
        String ingredients = getIngredients();
        if(ingredients.equals(""))
            return;
        Call<ArrayList<RecipeIDResponse>> call = callRecipe.callRecipeId(context.getString(R.string.api_key), ingredients,
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

    public void getRecipeByID(RecipeResponseListener listener, int id){
        CallRecipeByID callRecipeByID = retrofit.create(CallRecipeByID.class);
        Call<FullRecipeResponse> call = callRecipeByID.callRecipe(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<FullRecipeResponse>() {
            @Override
            public void onResponse(Call<FullRecipeResponse> call, Response<FullRecipeResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<FullRecipeResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRecipeByID{
        @GET("recipes/{id}/information")
        Call<FullRecipeResponse> callRecipe(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
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
