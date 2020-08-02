package com.whynot.cookbook.db.dao

import androidx.room.*
import com.whynot.cookbook.db.data.STEP_TABLE
import com.whynot.cookbook.db.data.Step

@Dao
interface StepDao {
    @Delete
    fun delete(vararg step: Step): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(step: Step): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(step: Step): Int

    @Query("SELECT * FROM $STEP_TABLE")
    fun getAll(): List<Step>
}