package com.robien.whattocook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import com.robien.whattocook.R

class HomepageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Get references to widgets
        var searchView = findViewById<SearchView>(R.id.ingredientSearchView)
        var noRecipesTextView = findViewById<TextView>(R.id.emptyTextView)
    }
}
