package com.robien.whattocook.api

import com.robien.whattocook.models.Recipe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SpoonacularAPI {
    @GET("apiKey=your-api-key-here")
    //fun getRecipesByIngredients(@Query("ingredients") ingredients: String) : Observable<List<Recipe>>

    fun getRecipesByIngredients(@Query("ingredients") ingredients: String): Observable<List<Recipe>>

}
