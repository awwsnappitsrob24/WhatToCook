package com.robien.whattocook.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robien.whattocook.R
import com.robien.whattocook.models.Recipe
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
            itemView.title.text = recipe.title
            itemView.image_link.text = recipe.imageLink
        }

    }
}
