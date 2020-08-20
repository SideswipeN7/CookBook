package com.whynot.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.whynot.cookbook.db.CookBookDatabase
import com.whynot.cookbook.db.dao.CategoryDao
import com.whynot.cookbook.db.dao.CategoryRecipeDao
import com.whynot.cookbook.db.dao.RecipeDao
import com.whynot.cookbook.db.data.Category
import com.whynot.cookbook.db.data.Recipe
import com.whynot.utils.TestUtil
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CategoryRecipeTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var recipeDao: RecipeDao
    private lateinit var categoryRecipeDao: CategoryRecipeDao
    private lateinit var db: CookBookDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CookBookDatabase::class.java
        ).build()
        categoryDao = db.categories()
        recipeDao = db.recipes()
        categoryRecipeDao = db.categoriesRecipes()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun correctFlow_on_crud() {
        var category1: Category = TestUtil.createCategory("testing category")
        var category2: Category = TestUtil.createCategory("testing category")
        var recipe: Recipe = TestUtil.createRecipe("Test Recipe")

        category1 = category1.copy(id = categoryDao.insert(category1))
        category2 = category2.copy(id = categoryDao.insert(category2))
        recipe = recipe.copy(id = recipeDao.insert(recipe))

        val join1 = TestUtil.joinCategoryRecipe(category1, recipe)
        val join2 = TestUtil.joinCategoryRecipe(category2, recipe)

        var inserted = categoryRecipeDao.insert(join1, join2)
        assertEquals(2, inserted.size)

        var deleted = categoryRecipeDao.delete(join1)
        assertEquals(1, deleted)

        deleted = categoryRecipeDao.delete(join2)
        assertEquals(1, deleted)

        inserted = categoryRecipeDao.insert(join1, join2)
        assertEquals(2, inserted.size)

        deleted = categoryRecipeDao.delete(join1, join2)
        assertEquals(2, deleted)
    }

    @Test
    fun noAction_on_deleteNonExisting() {
        val category: Category = TestUtil.createCategory("testing category", 1)
        val recipe: Recipe = TestUtil.createRecipe("Test Recipe", 1)

        val categoryRecipe = TestUtil.joinCategoryRecipe(category, recipe)

        val deleted = categoryRecipeDao.delete(categoryRecipe)

        assertEquals(0, deleted)
    }
}