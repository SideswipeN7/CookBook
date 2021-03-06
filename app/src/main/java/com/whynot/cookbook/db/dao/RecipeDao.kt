package com.whynot.cookbook.db.dao

import androidx.room.*
import com.whynot.cookbook.db.data.RECIPE_ID
import com.whynot.cookbook.db.data.RECIPE_TABLE
import com.whynot.cookbook.db.data.Recipe
import com.whynot.cookbook.db.data.RecipeWithDetails

@Dao
interface RecipeDao {
    @Delete
    fun delete(vararg recipe: Recipe): Int

    @Transaction
    @Query("SELECT * FROM $RECIPE_TABLE WHERE $RECIPE_ID = :recipeId")
    fun get(recipeId: Long): RecipeWithDetails

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipe: Recipe): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(recipe: Recipe): Int
}