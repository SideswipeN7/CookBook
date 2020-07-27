package com.whynot.cookbook.db.dao

import androidx.room.*
import com.whynot.cookbook.db.data.Step

@Dao
interface StepDao {
    @Delete
    fun delete(vararg step: Step): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg step: Step): Array<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg step: Step): Int
}