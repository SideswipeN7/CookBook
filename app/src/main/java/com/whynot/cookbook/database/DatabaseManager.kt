package com.whynot.cookbook.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.contentValuesOf
import com.whynot.cookbook.database.data.Category
import com.whynot.cookbook.database.data.Ingredient
import com.whynot.cookbook.database.data.Recipe
import com.whynot.cookbook.database.data.Step
import java.io.ByteArrayOutputStream


private const val BestQuality: Int = 100

class DatabaseManager(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE RECIPES(RECIPE_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME STRING, IMAGE BLOB, SYNOPSIS STRING, CATEGORY_ID INTEGER, FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORIES (CATEGORY_ID));")
        db!!.execSQL("CREATE TABLE CATEGORIES(CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME STRING, IMAGE BLOB);")
        db!!.execSQL("CREATE TABLE STEPS(STEP_ID INTEGER PRIMARY KEY AUTOINCREMENT, NUMBER INTEGER, SYNOPSIS STRING, RECIPE_ID INTEGER, FOREIGN KEY (RECIPE_ID) REFERENCES RECIPES (RECIPE_ID));")
        db!!.execSQL("CREATE TABLE INGREDIENTS(INGREDIENT_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME STRING, AMOUNT STRING, UNIT STRING, RECIPE_ID INTEGER, FOREIGN KEY (RECIPE_ID) REFERENCES RECIPES (RECIPE_ID));")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun addRecipe(recipe: Recipe): Long {
        val content = contentValuesOf(
            Pair("NAME", recipe.name),
            Pair("IMAGE", getByteFromBitmap(recipe.image)),
            Pair("SYNOPSIS", recipe.synopsis),
            Pair("CATEGORY_ID", recipe.categoryId)
        )
        return writableDatabase.insertOrThrow("RECIPES", null, content)
    }

    fun addCategory(category: Category): Long {
        val content = contentValuesOf(
            Pair("NAME", category.name),
            Pair("IMAGE", getByteFromBitmap(category.image))
        )
       return  writableDatabase.insertOrThrow("CATEGORIES", null, content)
    }

    fun addStep(step: Step): Long {
        val content = contentValuesOf(
            Pair("NUMBER", step.number),
            Pair("SYNOPSIS", step.synopsis),
            Pair("RECIPE_ID", step.recipeId)
        )
        return writableDatabase.insertOrThrow("STEPS", null, content)
    }

    fun addIngredient(ingredient: Ingredient): Long {
        val content = contentValuesOf(
            Pair("NAME", ingredient.name),
            Pair("AMOUNT", ingredient.amount),
            Pair("UNIT", ingredient.unit)
        )
        return writableDatabase.insertOrThrow("INGREDIENTS", null, content)
    }

    fun getRecipes(): ArrayList<Recipe> {
        val list = ArrayList<Recipe>()
        val columns = arrayOf(
            "RECIPE_ID",
            "NAME",
            "IMAGE",
            "SYNOPSIS",
            "CATEGORY_ID"
        )
        val cursor = readableDatabase.query(
            "RECIPES",
            columns,
            null,
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            list.add(
                Recipe(
                    cursor.getInt(0),
                    cursor.getString(1),
                    bitmapFromBlob(cursor.getBlob(2)),
                    cursor.getString(3),
                    cursor.getInt(4)
                )
            )
        }

        return list
    }

    fun getCategories(): ArrayList<Category> {
        val list = ArrayList<Category>()
        val columns = arrayOf(
            "CATEGORY_ID",
            "NAME",
            "IMAGE"
        )
        val cursor = readableDatabase.query(
            "CATEGORIES",
            columns,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            list.add(
                Category(
                    cursor.getInt(0),
                    cursor.getString(1),
                    bitmapFromBlob(cursor.getBlob(2))
                )
            )
        }

        return list
    }

    fun getSteps(): ArrayList<Step> {
        val list = ArrayList<Step>()
        val columns = listOf(
            "RECIPE_ID",
            "NUMBER",
            "SYNOPSIS"
        )
        val cursor = readableDatabase.query(
            "STEPS",
            columns.toTypedArray(),
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            list.add(
                Step(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3)
                )
            )
        }

        return list
    }

    fun getIngredients(): ArrayList<Ingredient> {
        val list = ArrayList<Ingredient>()
        val columns = arrayOf(
            "INGREDIENT_ID",
            "NAME",
            "AMOUNT",
            "UNIT",
            "RECIPE_ID"
        )
        val cursor = readableDatabase.query(
            "INGREDIENTS",
            columns,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            list.add(
                Ingredient(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4)
                )
            )
        }

        return list
    }

    fun updateRecipe(recipe: Recipe) {
        val content = contentValuesOf(
            Pair("RECIPE_ID", recipe.id),
            Pair("NAME", recipe.name),
            Pair("IMAGE", getByteFromBitmap(recipe.image)),
            Pair("SYNOPSIS", recipe.synopsis),
            Pair("CATEGORY_ID", recipe.categoryId)
        )
        val args = arrayOf(recipe.id.toString())
        writableDatabase.update("RECIPES", content, "RECIPE_ID=?", args)
    }

    fun updateCategory(category: Category) {
        val content = contentValuesOf(
            Pair("CATEGORY_ID", category.id),
            Pair("NAME", category.name),
            Pair("IMAGE", getByteFromBitmap(category.image))
        )
        val args = arrayOf(category.id.toString())
        writableDatabase.update("CATEGORIES", content, "CATEGORY_ID=?", args)
    }

    fun updateStep(step: Step) {
        val content = contentValuesOf(
            Pair("STEP_ID", step.id),
            Pair("NUMBER", step.number),
            Pair("SYNOPSIS", step.synopsis),
            Pair("RECIPE_ID", step.recipeId)
        )
        val args = arrayOf(step.id.toString())
        writableDatabase.update("STEPS", content, "STEP_ID=?", args)
    }

    fun updateIngredients(ingredient: Ingredient) {
        val content = contentValuesOf(
            Pair("INGREDIENT_ID", ingredient.id),
            Pair("NAME", ingredient.name),
            Pair("AMOUNT", ingredient.amount),
            Pair("UNIT", ingredient.unit),
            Pair("RECIPE_ID", ingredient.recipeId)
        )
        val args = arrayOf(ingredient.id.toString())
        writableDatabase.update("INGREDIENTS", content, "INGREDIENT_ID=?", args)
    }

    fun removeRecipe(recipe: Recipe) {
        val db = writableDatabase
        val args = arrayOf(recipe.id.toString())
        db.delete("RECIPES", "RECIPE_ID=?", args)
    }

    fun removeCategory(category: Category) {
        val db = writableDatabase
        val args = arrayOf(category.id.toString())
        db.delete("CATEGORIES", "CATEGORY_ID=?", args)
    }

    fun removeStep(step: Step) {
        val db = writableDatabase
        val args = arrayOf(step.id.toString())
        db.delete("STEPS", "STEP_ID=?", args)
    }

    fun removeIngredient(ingredient: Ingredient) {
        val db = writableDatabase
        val args = arrayOf(ingredient.id.toString())
        db.delete("INGREDIENTS", "INGREDIENT_ID=?", args)
    }

    private fun getByteFromBitmap(bitmap: Bitmap?): ByteArray {
        if (bitmap == null) {
            return byteArrayOf()
        }

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, BestQuality, stream)
        return stream.toByteArray()
    }

    private fun bitmapFromBlob(bytes: ByteArray): Bitmap? {
        val options = BitmapFactory.Options()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    }
}