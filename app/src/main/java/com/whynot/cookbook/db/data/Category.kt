package com.whynot.cookbook.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = CATEGORY_TABLE)
data class Category(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = CATEGORY_ID) @NotNull val id: Long?,
    @ColumnInfo(name = CATEGORY_NAME) val name: String
)