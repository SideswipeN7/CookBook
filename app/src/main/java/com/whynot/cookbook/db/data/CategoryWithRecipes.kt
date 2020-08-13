package com.whynot.cookbook.db.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CategoryWithRecipes(
    @Embedded val category: Category,
    @Relation(
        parentColumn = CATEGORY_ID,
        entity = Recipe::class,
        entityColumn = RECIPE_ID,
        associateBy = Junction(CategoryRecipe::class)
    )
    val recipes: List<Recipe>//TODO: https://stackoverflow.com/questions/44815784/room-persistent-database-how-to-insert-list-of-items-into-db-when-it-has-no-pr
)