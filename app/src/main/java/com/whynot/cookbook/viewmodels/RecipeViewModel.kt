package com.whynot.cookbook.viewmodels

import android.graphics.Bitmap
import com.whynot.cookbook.database.data.Ingredient
import com.whynot.cookbook.database.data.Recipe
import com.whynot.cookbook.database.data.Step


class RecipeViewModel(
    private var _recipe: Recipe,
    private var _steps: List<Step>,
    private var _ingredients: List<Ingredient>
) {
    var name: String
        get() = _recipe.name
        private set(value) {
            _recipe.name = value
        }

    var synopsis: String
        get() = _recipe.synopsis
        private set(value) {
            _recipe.synopsis = value
        }

    var image: Bitmap
        get() = _recipe.image!!
        private set(value) {
            _recipe.image = value
        }

    var steps: List<Step>
        get() = _steps
        private set(value) {
            _steps = value
        }

    var ingredients: List<Ingredient>
        get() = _ingredients
        private set(value) {
            _ingredients = value
        }
}