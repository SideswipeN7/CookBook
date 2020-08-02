package com.whynot.cookbook.db.dao

import androidx.room.*
import com.whynot.cookbook.db.data.INGREDIENT_TABLE
import com.whynot.cookbook.db.data.Ingredient
import com.whynot.cookbook.db.data.Step

@Dao
interface IngredientDao {
    @Delete
    fun delete(vararg ingredient: Ingredient): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: Ingredient): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(ingredient: Ingredient): Int

    @Query("SELECT * FROM $INGREDIENT_TABLE")
    fun getAll(): List<Ingredient>
}