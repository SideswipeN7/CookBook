package com.whynot.cookbook.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = RECIPE_TABLE)
data class Recipe(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = RECIPE_ID) @NotNull val id: Long?,
    @ColumnInfo(name = RECIPE_NAME) val name: String,
    @ColumnInfo(name = RECIPE_IMAGE) val image: ByteArray?,
    @ColumnInfo(name = RECIPE_SYNOPSIS) val synopsis: String,
    @ColumnInfo(name = RECIPE_REF_ID_CATEGORY) @NotNull val categoryId: Long?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (id != other.id) return false
        if (name != other.name) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (synopsis != other.synopsis) return false
        if (categoryId != other.categoryId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + synopsis.hashCode()
        result = 31 * result + (categoryId?.hashCode() ?: 0)

        return result
    }
}