package com.whynot.cookbook.db.dao

import androidx.room.*
import com.whynot.cookbook.db.data.STEP_TABLE
import com.whynot.cookbook.db.data.Step

@Dao
interface StepDao {
    @Delete
    fun delete(vararg step: Step): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(step: Step): Long

    @Query("SELECT * FROM $STEP_TABLE")
    fun get(): List<Step>
}