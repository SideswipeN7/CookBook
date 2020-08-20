package com.whynot.cookbook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.whynot.cookbook.db.dao.*
import com.whynot.cookbook.db.data.*

private const val DATABASE = "cookBook.db"

@Database(
    entities = [
        Category::class,
        CategoryRecipe::class,
        Ingredient::class,
        Step::class,
        Recipe::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CookBookDatabase : RoomDatabase() {
    abstract fun categories(): CategoryDao
    abstract fun categoriesRecipes(): CategoryRecipeDao
    abstract fun ingredients(): IngredientDao
    abstract fun recipes(): RecipeDao
    abstract fun steps(): StepDao

    companion object {
        @Volatile
        private var instance: CookBookDatabase? = null

        fun getInstance(context: Context): CookBookDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CookBookDatabase {
            return Room.databaseBuilder(context, CookBookDatabase::class.java, DATABASE)
                .build()
        }
    }
}