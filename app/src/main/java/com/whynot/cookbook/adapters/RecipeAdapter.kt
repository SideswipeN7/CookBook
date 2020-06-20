package com.whynot.cookbook.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.whynot.cookbook.R
import com.whynot.cookbook.data.Recipe


class RecipeAdapter(var recipeList: List<Recipe>) : Adapter<RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecipeViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe: Recipe = recipeList[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipeList.size
}

class RecipeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recipe_item, parent, false)) {
    private var mTitleView: TextView? = null
    private var mImageView: ImageView? = null


    init {
        mTitleView = itemView.findViewById(R.id.recipe_name)
        mImageView = itemView.findViewById(R.id.recipe_image)
    }

    fun bind(recipe: Recipe) {
        mTitleView?.text = recipe.name
        if (recipe.image != null) {
            mImageView = recipe.image
        }
    }
}
