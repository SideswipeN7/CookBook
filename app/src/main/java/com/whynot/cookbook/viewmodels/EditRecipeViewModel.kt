package com.whynot.cookbook.viewmodels

import android.graphics.Bitmap
import android.view.View.NO_ID
import androidx.lifecycle.ViewModel
import com.whynot.cookbook.database.data.Ingredient
import com.whynot.cookbook.database.data.Recipe
import com.whynot.cookbook.database.data.Step
import com.whynot.cookbook.services.RecipeService

class EditRecipeViewModel(private val recipeService: RecipeService) : ViewModel() {
    val steps: ArrayList<Step> = ArrayList()
    val ingredients: ArrayList<Ingredient> = ArrayList()
    var name: String? = null
    var synopsis: String? = null
    var image: Bitmap? = null
    var isEdit: Boolean = false
    var categoryId: Int = NO_ID
    var id: Int = NO_ID

    fun clear() {
        id = NO_ID
        categoryId = NO_ID
        name = ""
        synopsis = ""
        steps.clear()
        ingredients.clear()
        isEdit = false
    }

    fun loadRecipe(recipeId: Int) {
        val recipe = recipeService.getRecipe(recipeId)
        id = recipe.id
        categoryId = recipe.categoryId
        name = recipe.name
        synopsis = recipe.synopsis
        image = recipe.image
        isEdit = true

        recipeService.getIngredients(recipe).forEach { ingredients.add(it.copy()) }
        recipeService.getSteps(recipe).forEach { steps.add(it.copy()) }
        steps.sortBy { it.number }
        ingredients.sortBy { it.name }
    }

    fun saveRecipe() {
        recipeService.saveRecipe(
            Recipe(id, name!!, image, synopsis!!, categoryId),
            steps,
            ingredients
        )
    }

}

