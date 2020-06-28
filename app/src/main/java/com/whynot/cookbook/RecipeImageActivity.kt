package com.whynot.cookbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.whynot.cookbook.services.RecipeService
import kotlinx.android.synthetic.main.activity_recipe_image.*
import kotlinx.android.synthetic.main.recipe_item.recipe_image
import org.koin.android.ext.android.inject

class RecipeImageActivity : AppCompatActivity() {
    private val recipeService: RecipeService by inject()
//    private lateinit var viewModel: RecipeImageViewModel

    companion object {
        const val RECIPE_ID = "recipe_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_image)
        val recipe = recipeService.getRecipe(intent.getIntExtra(RECIPE_ID, 0))

        recipe_image.setImageBitmap(recipe.image)
        recipe_image.setOnClickListener {
            //TODO: set new image via viewModel
        }

        btn_accept.setOnClickListener{
            //TODO: change image
        }

        btn_cancel.setOnClickListener{
            finish()
        }
    }
}