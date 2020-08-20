package com.whynot.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.whynot.cookbook.db.CookBookDatabase
import com.whynot.cookbook.db.dao.IngredientDao
import com.whynot.cookbook.db.dao.RecipeDao
import com.whynot.cookbook.db.dao.StepDao
import com.whynot.cookbook.db.data.Ingredient
import com.whynot.cookbook.db.data.Recipe
import com.whynot.cookbook.db.data.Step
import com.whynot.utils.TestUtil
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RecipeTest {
    private lateinit var recipeDao: RecipeDao
    private lateinit var ingredientDao: IngredientDao
    private lateinit var stepDao: StepDao
    private lateinit var db: CookBookDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CookBookDatabase::class.java
        ).build()
        recipeDao = db.recipes()
        ingredientDao = db.ingredients()
        stepDao = db.steps()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun correctFlow_on_crud() {
        var recipe1: Recipe = TestUtil.createRecipe("testing recipe 1")
        var recipe2: Recipe = TestUtil.createRecipe("testing recipe 2")

        val insertedId1 = recipeDao.insert(recipe1)
        val insertedId2 = recipeDao.insert(recipe2)
        recipe1 = recipe1.copy(id = insertedId1)
        recipe2 = recipe2.copy(id = insertedId2)

        val dbRecipe1 = recipeDao.get(insertedId1)

        assertEquals(dbRecipe1.recipe.name, recipe1.name)

        val deleted = recipeDao.delete(recipe1)
        assertEquals(1, deleted)

        val step1: Step = TestUtil.createStep("test", null, 0, recipe2.id)
        val step2: Step = TestUtil.createStep("test", null, 0, recipe2.id)
        stepDao.upsert(step1)
        stepDao.upsert(step2)

        val ingredient1: Ingredient = TestUtil.createIngredient("test", null, "", recipe2.id)
        ingredientDao.upsert(ingredient1)

        val dbRecipe2 = recipeDao.get(insertedId2)

        assertEquals(dbRecipe2.recipe.name, recipe2.name)
        assertEquals(2, dbRecipe2.steps.size)
        assertEquals(1, dbRecipe2.ingredients.size)
    }

    @Test
    fun noAdd_on_insertDuplicate() {
        val recipe1: Recipe = TestUtil.createRecipe("testing recipe 1")

        val insertedId1 = recipeDao.insert(recipe1)
        val recipe2 = recipe1.copy(id = insertedId1, name = "recipe clone")

        val inserted = recipeDao.insert(recipe2)

        assertEquals(-1, inserted)
    }

    @Test
    fun insert_on_updateNonExisting() {
        val recipe: Recipe = TestUtil.createRecipe("testing recipe 1", 1)

        val updated = recipeDao.update(recipe)

        assertEquals(0, updated)
    }

    @Test
    fun noAction_on_deleteNonExisting() {
        val recipe: Recipe = TestUtil.createRecipe("testing recipe 1", 1)

        val deleted = recipeDao.delete(recipe)

        assertEquals(0, deleted)
    }
}