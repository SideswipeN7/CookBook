package com.whynot.cookbook.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.whynot.cookbook.R
import com.whynot.cookbook.RecipeActivity
import com.whynot.cookbook.database.data.Recipe


class RecipeAdapter : Adapter<RecipeViewHolder>() {
    var recipeList: List<Recipe>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecipeViewHolder(inflater, parent, parent.context)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        if (recipeList == null) {
            return
        }
        val recipe: Recipe = recipeList!![position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipeList!!.size
}

class RecipeViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val context: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recipe_item, parent, false)) {
    private var mTitleView: TextView = itemView.findViewById(R.id.recipe_name)
    private var mImageView: ImageView = itemView.findViewById(R.id.recipe_image)
    private var mCardView: CardView = itemView.findViewById(R.id.recipe_view)


    fun bind(recipe: Recipe) {
        mTitleView.text = recipe.name
        if (recipe.image != null) {
            mImageView.setImageBitmap(recipe.image)
        }

        mCardView.setOnClickListener {
            val intent = RecipeActivity.newIntent(context, recipe)
            startActivity(context, intent, null)
        }

        mCardView.setOnLongClickListener {
            mCardView.isSelected = true

            return@setOnLongClickListener true
        }
    }
}
