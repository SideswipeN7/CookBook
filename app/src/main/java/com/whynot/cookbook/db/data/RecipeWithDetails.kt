package com.whynot.cookbook.db.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RecipeWithDetails(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = RECIPE_ID,
        entity = Category::class,
        entityColumn = CATEGORY_ID,
        associateBy = Junction(CategoryRecipe::class)
    )
    val categories: List<Category>,
    @Relation(
        parentColumn = RECIPE_ID,
        entity = Ingredient::class,
        entityColumn = INGREDIENT_REF_ID_RECIPE
    )
    val ingredients: List<Ingredient>,
    @Relation(
        parentColumn = RECIPE_ID,
        entity = Step::class,
        entityColumn = STEP_REF_ID_RECIPE
    )
    val steps: List<Step>
)
