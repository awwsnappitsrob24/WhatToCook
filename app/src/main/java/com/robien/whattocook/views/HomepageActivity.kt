package com.robien.whattocook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import io.reactivex.disposables.CompositeDisposable
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import com.robien.whattocook.R
import com.robien.whattocook.api.SpoonacularAPI
import com.robien.whattocook.models.Recipe
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity(), RecipeAdapter.Listener {

    private var recipeAdapter: RecipeAdapter? = null
    private var recipeCompositeDisposable: CompositeDisposable? = null
    private var recipeArrayList: ArrayList<Recipe>? = null
    private val BASE_URL = "https://api.spoonacular.com/recipes/"
    private var ingredientsQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Get references to search view for retrieval of query
        val searchView = findViewById<SearchView>(R.id.ingredientSearchView)
        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                //Set the value in the search view to the query string
                ingredientsQuery = query
                recipeCompositeDisposable = CompositeDisposable()
                initRecyclerView()
                loadRecipes(ingredientsQuery)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                // Basically do nothing
                return false;
            }

        })
    }

    // Initialize the RecyclerView//
    private fun initRecyclerView() {
        // Use a layout manager to position your items to look like a standard ListView
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recipe_list.layoutManager = layoutManager
    }

    //Implement load recipes
    private fun loadRecipes(ingredients: String) {
        Log.d("load recipes", ingredients)

        //Define the Retrofit request
        val requestInterface = Retrofit.Builder()

            //Set the API’s base URL
            .baseUrl(BASE_URL)

            //Specify the converter factory to use for serialization and deserialization
            .addConverterFactory(GsonConverterFactory.create())

            //Add a call adapter factory to support RxJava return types
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            //Build the Retrofit instance
            .build().create(SpoonacularAPI::class.java)

        //Add all RxJava disposables to a CompositeDisposable
        //Pass in the user query from the search bar
        recipeCompositeDisposable?.add(requestInterface.getRecipesByIngredients(ingredients)

            //Send the Observable’s notifications to the main UI thread
            .observeOn(AndroidSchedulers.mainThread())

            //Subscribe to the Observer away from the main UI thread
            .subscribeOn(Schedulers.io())
            .subscribe( { recipeArrayList -> handleResponse(recipeArrayList)},
                {t: Throwable? ->  Toast.makeText(applicationContext, t?.message,
                    Toast.LENGTH_LONG).show()}))

    }

    private fun handleResponse(recipeList: List<Recipe>) {
        recipeArrayList = ArrayList(recipeList)
        recipeAdapter = RecipeAdapter(recipeArrayList!!, this)

        //Set the adapter
        recipe_list.adapter = recipeAdapter
    }

    override fun onItemClick(recipe: Recipe) {

        //If the user clicks on an item, then display a Toast
        Toast.makeText(this, "You clicked: ${recipe.title}", Toast.LENGTH_LONG).show()

    }

    override fun onDestroy() {
        super.onDestroy()

        //Clear all your disposables//
        recipeCompositeDisposable?.clear()

    }
}
