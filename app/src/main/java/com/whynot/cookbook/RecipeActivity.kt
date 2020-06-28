package com.whynot.cookbook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.whynot.cookbook.database.data.Recipe
import com.whynot.cookbook.services.RecipeService
import com.whynot.cookbook.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.activity_recipe.*
import org.koin.android.ext.android.inject


class RecipeActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA = "recipe_id"
        private const val NO_ID = -1

        fun newIntent(context: Context, recipe: Recipe): Intent {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra(EXTRA, recipe.id)

            return intent
        }

        private fun getExtra(intent: Intent): Int = intent.getIntExtra(EXTRA, NO_ID)
    }

    private val recipeService: RecipeService by inject()
    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipeId = getExtra(intent)
        val recipe = recipeService.getRecipe(recipeId)
        viewModel = RecipeViewModel(
            recipe,
            recipeService.getSteps(recipe),
            recipeService.getIngredients(recipe)
        )

        title = viewModel.name
        recipe_name.text = viewModel.name
        recipe_synopsis.text = viewModel.synopsis

//        TODO: remove later
        if (image != null)
            image.setImageBitmap(viewModel.image)

        button.setOnClickListener {
            val intent = Intent(this, RecipeImageActivity::class.java)
            intent.putExtra(RecipeImageActivity.RECIPE_ID, viewModel.name)
            ContextCompat.startActivity(this, intent, null)
        }
    }


}