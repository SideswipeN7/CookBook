package com.whynot.cookbook.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = INGREDIENT_TABLE)
data class Ingredient(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = INGREDIENT_ID) @NotNull val id: Long?,
    @ColumnInfo(name = INGREDIENT_NAME) val name: String,
    @ColumnInfo(name = INGREDIENT_VALUE) val value: String,
    @ColumnInfo(name = INGREDIENT_REF_ID_RECIPE) @NotNull val recipeId: Long?
)