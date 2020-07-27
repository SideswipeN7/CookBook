package com.whynot.cookbook.db.dao

import androidx.room.*
import com.whynot.cookbook.db.data.Ingredient

@Dao
interface IngredientDao {
    @Delete
    fun delete(vararg ingredient: Ingredient): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg ingredient: Ingredient): Array<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg ingredient: Ingredient): Int
}