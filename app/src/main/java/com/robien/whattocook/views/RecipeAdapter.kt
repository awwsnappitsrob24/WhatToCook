package com.robien.whattocook.views

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robien.whattocook.R
import com.robien.whattocook.models.Recipe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout.view.*


class RecipeAdapter (private val recipeList : ArrayList<Recipe>, private val listener :

    //Extend RecyclerView.Adapter//
    Listener) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(recipe: Recipe)
    }

    //Bind the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Pass the position where each item should be displayed//
        holder.bind(recipeList[position], listener, position)
    }

    //Check how many items you have to display
    override fun getItemCount(): Int = recipeList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)

    }

    //Create a ViewHolder class for your RecyclerView items
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        //Assign values from the data model, to their corresponding Views
        fun bind(recipe: Recipe, listener: Listener, position: Int) {

            //Listen for user input events//
            itemView.setOnClickListener{ listener.onItemClick(recipe) }
            itemView.title.text = recipe.title.capitalize()

            // download image from URL
            Picasso.get()
                .load(recipe.image)
                .resize(300,300)
                .centerCrop()
                .transform(CircleTransform(50,0))
                .into(itemView.recipeImage)

            // Get references to the two buttons
            itemView.addRecipeButton.setOnClickListener {
                // add recipe to wish list
                Log.d("clicked add", "Add")
            }

            itemView.viewRecipeButton.setOnClickListener {
                // view recipe
                Log.d("clicked view", "View")
            }
        }

    }
}
