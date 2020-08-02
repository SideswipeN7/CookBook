package com.whynot.cookbook.db.dao

import androidx.room.*
import com.whynot.cookbook.db.data.CATEGORY_ID
import com.whynot.cookbook.db.data.CATEGORY_TABLE
import com.whynot.cookbook.db.data.Category
import com.whynot.cookbook.db.data.CategoryWithRecipes

@Dao
interface CategoryDao {
    @Delete
    fun delete(vararg category: Category): Int

    @Query("SELECT * FROM $CATEGORY_TABLE")
    fun getAll(): List<Category>

    @Query("SELECT * FROM $CATEGORY_TABLE WHERE $CATEGORY_ID = :categoryId")
    fun get(categoryId: Long): Category

    @Transaction
    @Query("SELECT * FROM $CATEGORY_TABLE WHERE $CATEGORY_ID IN (:categoryIds)")
    fun getWithRecipes(categoryIds: Long): CategoryWithRecipes

    @Transaction
    @Query("SELECT * FROM $CATEGORY_TABLE")
    fun getAllWithRecipes(): CategoryWithRecipes

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(category: Category): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(category: Category): Int
}