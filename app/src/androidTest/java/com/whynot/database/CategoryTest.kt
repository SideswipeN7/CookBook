package com.whynot.database

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.whynot.cookbook.db.CookBookDatabase
import com.whynot.cookbook.db.dao.CategoryDao
import com.whynot.cookbook.db.dao.CategoryRecipeDao
import com.whynot.cookbook.db.dao.RecipeDao
import com.whynot.cookbook.db.data.Category
import com.whynot.utils.TestUtil
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CategoryTest {
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
        val category1: Category = TestUtil.createCategory("testing category")
        val category2: Category = TestUtil.createCategory("testing category")

        val insertedId1 = categoryDao.insert(category1)
        val insertedId2 = categoryDao.insert(category2)
        var all = categoryDao.get()

        assertEquals(2, all.size)
        assertEquals(insertedId1, all[0].id)
        assertEquals(insertedId2, all[1].id)

        val selected = categoryDao.get(insertedId1)
        assertEquals(selected.id, all[0].id)
        assertEquals(selected.name, all[0].name)


        val deleted = categoryDao.delete(all[1])
        assertEquals(1, deleted)

        all = categoryDao.get()
        assertEquals(1, all.size)
        assertEquals(category1.name, all[0].name)

        val newCategoryName = "test"
        val toUpdate = all[0].copy(name = newCategoryName)

        val updatedIds = categoryDao.update(toUpdate)
        assertEquals(1, updatedIds)

        all = categoryDao.get()
        assertEquals(newCategoryName, all[0].name)

        var recipe1 = TestUtil.createRecipe("Test 1", null, "", all[0].id)
        var recipe2 = TestUtil.createRecipe("Test 2", null, "", all[0].id)

        val recipe1Id = recipeDao.insert(recipe1)
        val recipe2Id = recipeDao.insert(recipe2)

        recipe1 = recipe1.copy(id = recipe1Id)
        recipe2 = recipe2.copy(id = recipe2Id)

        val join1 = TestUtil.joinCategoryRecipe(all[0], recipe1)
        val join2 = TestUtil.joinCategoryRecipe(all[0], recipe2)

        categoryRecipeDao.insert(join1)
        categoryRecipeDao.insert(join2)

        val category3 = TestUtil.createCategory("Category 3")
        categoryDao.insert(category3)

        val data = categoryDao.getAllWithRecipes()

        assertEquals(2, data.size)
        assertEquals(recipe1, data[0].recipes[0])
        assertEquals(recipe2, data[0].recipes[1])
        assertEquals(0, data[1].recipes.size)
    }

    @Test
    fun throw_on_insertDuplicate() {
        var exceptionThrown = false
        val category1: Category = TestUtil.createCategory("testing category")
        val newId = categoryDao.insert(category1)
        val category2 = category1.copy(id = newId)

        try {
            categoryDao.insert(category2)
        } catch (ex: Exception) {
            exceptionThrown = true
            assertEquals(SQLiteConstraintException::class.java, ex::class.java)
        }

        assertTrue(exceptionThrown)

        exceptionThrown = false
        val category3 = category2.copy(name = "Other name")

        try {
            categoryDao.insert(category3)
        } catch (ex: Exception) {
            exceptionThrown = true
            assertEquals(SQLiteConstraintException::class.java, ex::class.java)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun insert_on_updateNonExisting() {
        val category: Category = TestUtil.createCategory("testing category").copy(id = 1)

        categoryDao.update(category)

        val all = categoryDao.get()

        assertEquals(0, all.size)
    }

    @Test
    fun noAction_on_deleteNonExisting() {
        val category: Category = TestUtil.createCategory("testing category").copy(id = 1)

        categoryDao.delete(category)

        val all = categoryDao.get()

        assertEquals(0, all.size)
    }
}