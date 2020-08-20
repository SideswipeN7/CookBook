package com.whynot.cookbook.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.whynot.cookbook.db.data.CategoryRecipe

@Dao
interface CategoryRecipeDao {
    @Delete
    fun delete(vararg categoryRecipe: CategoryRecipe): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg categoryRecipe: CategoryRecipe): List<Long>
}