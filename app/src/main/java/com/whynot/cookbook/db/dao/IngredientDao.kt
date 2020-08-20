package com.whynot.cookbook.db.dao

import androidx.room.*
import com.whynot.cookbook.db.data.INGREDIENT_TABLE
import com.whynot.cookbook.db.data.Ingredient

@Dao
interface IngredientDao {
    @Delete
    fun delete(vararg ingredient: Ingredient): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(ingredient: Ingredient): Long

    @Query("SELECT * FROM $INGREDIENT_TABLE")
    fun get(): List<Ingredient>
}