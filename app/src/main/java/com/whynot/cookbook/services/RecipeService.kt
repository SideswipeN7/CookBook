package com.whynot.cookbook.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.whynot.cookbook.database.DatabaseManager
import com.whynot.cookbook.database.data.Category
import com.whynot.cookbook.database.data.Ingredient
import com.whynot.cookbook.database.data.Recipe
import com.whynot.cookbook.database.data.Step
import org.koin.android.ext.android.inject
import java.lang.Exception
import java.util.function.BiPredicate
import java.util.function.Predicate

class RecipeService :
    Service() {
    companion object {
        private const val TAG: String = "Recipe_Service"
    }

    private val databaseManager: DatabaseManager by inject()
    private var recipes: ArrayList<Recipe>
    private var categories: ArrayList<Category>
    private var ingredients: ArrayList<Ingredient>
    private var steps: ArrayList<Step>

    init {
        recipes = databaseManager.getRecipes()
        categories = databaseManager.getCategories()
        ingredients = databaseManager.getIngredients()
        steps = databaseManager.getSteps()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    fun getRecipes(): List<Recipe> = recipes

    fun refresh() {
        recipes = databaseManager.getRecipes()
        categories = databaseManager.getCategories()
        ingredients = databaseManager.getIngredients()
        steps = databaseManager.getSteps()
    }

    fun getRecipe(recipeId: Int): Recipe = recipes.first { r -> r.id == recipeId }

    fun saveRecipe(recipe: Recipe, steps: List<Step>, ingredients: List<Ingredient>) {
        try {
            val newRecipe = updateRecipe(recipe)

            updateSteps(newRecipe, steps)
            updateIngredients(newRecipe, ingredients)
        } catch (exception: Exception) {
            Log.e(TAG, "Error on add Recipe, error message: ${exception.message}")
        }
    }

    fun getSteps(recipe: Recipe): List<Step> = steps.filter { s -> s.recipeId == recipe.id }

    fun getIngredients(recipe: Recipe): List<Ingredient> =
        ingredients.filter { i -> i.recipeId == recipe.id }

    private fun updateSteps(recipe: Recipe, steps: List<Step>) {
        steps.filter { s -> s.recipeId == null }.forEach {
            databaseManager.updateStep(it)
        }

        steps.filter { s -> s.recipeId != null }.forEach {
            it.recipeId = recipe.id
            databaseManager.addStep(it)
        }

        steps.forEach {
            updateList(this.steps, { i -> i.id == it.id }, it)
        }
    }

    private fun updateIngredients(recipe: Recipe, ingredients: List<Ingredient>) {
        ingredients.filter { i -> i.recipeId == null }.forEach {
            databaseManager.updateIngredients(it)
        }

        ingredients.filter { i -> i.recipeId != null }.forEach {
            it.recipeId = recipe.id
            databaseManager.addIngredient(it)
        }

        ingredients.forEach {
            updateList(this.ingredients, { i -> i.id == it.id }, it)
        }
    }

    private fun updateRecipe(recipe: Recipe): Recipe {
        val id = databaseManager.addRecipe(recipe)
        if (recipe.id != id.toInt()) {
            recipe.id = id.toInt()
        }

        updateList(recipes, { r -> r.id == recipe.id }, recipe)

        return recipe
    }

    private fun <T> updateList(list: ArrayList<T>, predicate: (T) -> Boolean, item: T) {
        val current = list.firstOrNull(predicate = predicate)
        if (current == null) {
            list.add(item)
        } else {
            val index = list.indexOf(item)
            list[index] = item
        }
    }
}
