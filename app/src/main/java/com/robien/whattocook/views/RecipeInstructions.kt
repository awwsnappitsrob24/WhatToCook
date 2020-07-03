package com.robien.whattocook.views

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.robien.whattocook.R
import com.squareup.picasso.Picasso
import android.util.DisplayMetrics

class RecipeInstructions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_instructions)

        val recipeImageURL = intent.getStringExtra("recipe image")
        val recipeTitle = intent.getStringExtra("recipe title")

        // Get width of the screen to fill out the screen with the image
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        // download image from URL and set it to the image view
        val recipeImageView = findViewById<ImageView>(R.id.recipeImage)
        Picasso.get()
            .load(recipeImageURL)
            .resize(displayMetrics.widthPixels - 100,displayMetrics.heightPixels/3)
            .centerCrop()
            .into(recipeImageView)

        // Set the text view to the clicked recipe title
        val recipeTextView = findViewById<TextView>(R.id.recipeTitle)
        recipeTextView.text = recipeTitle
        recipeTextView.setTextColor(Color.BLACK)
    }
}