package com.robien.whattocook.api

import com.robien.whattocook.models.Recipe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SpoonacularAPI {
    //@GET("apiKey=your-api-key-here")
    //fun getRecipesByIngredients(@Query("ingredients") ingredients: String) : Observable<List<Recipe>>
    @GET("https://api.spoonacular.com/recipes/findByIngredients?number=10&apiKey=2b575e2b5634470faafe173d4145ea37")
    fun getRecipesByIngredients(@Query("ingredients") ingredients: String): Observable<List<Recipe>>

}
