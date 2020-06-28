package com.whynot.cookbook.database.data

data class Ingredient(
    var id: Int,
    var name: String,
    var amount: String,
    var unit: String,
    var recipeId: Int?
)