package com.whynot.cookbook.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = [CATEGORY_ID, RECIPE_ID]
)
data class CategoryRecipe(
    @ColumnInfo(name = CATEGORY_ID) val categoryRefId: Long,
    @ColumnInfo(name = RECIPE_ID) val recipeRefId: Long
)