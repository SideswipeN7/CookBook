package com.whynot.cookbook.db.dao

import androidx.room.Dao
import androidx.room.Delete
import com.whynot.cookbook.db.data.CategoryRecipe

@Dao
interface CategoryRecipeDao {
    @Delete
    fun delete(vararg categoryRecipe: CategoryRecipe): Int
}