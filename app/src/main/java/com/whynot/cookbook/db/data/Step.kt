package com.whynot.cookbook.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = STEP_TABLE)
data class Step(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = STEP_ID) @NotNull  val id: Long?,
    @ColumnInfo(name = STEP_NUMBER) val number: Int,
    @ColumnInfo(name = STEP_SYNOPSIS) val synopsis: String,
    @ColumnInfo(name = STEP_REF_ID_RECIPE) @NotNull val recipeId: Long?
)