package com.robien.whattocook.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.robien.whattocook.R

class RecipeInstructions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_instructions)

        val recipeTextView = findViewById<TextView>(R.id.recipeTextView)

        val recipeTitle = intent.getStringExtra("recipe title")
        recipeTextView.text = recipeTitle
    }
}