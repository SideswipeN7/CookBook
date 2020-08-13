package com.whynot.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.whynot.cookbook.db.CookBookDatabase
import com.whynot.cookbook.db.dao.IngredientDao
import com.whynot.cookbook.db.data.Ingredient
import com.whynot.utils.TestUtil
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class IngredientTest {
    private lateinit var ingredientDao: IngredientDao
    private lateinit var db: CookBookDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CookBookDatabase::class.java
        ).build()
        ingredientDao = db.ingredients()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun correctFlow_on_writeReadDeleteUpdate() {
        var ingredient1: Ingredient = TestUtil.createIngredient("testing ingredient 1")
        var ingredient2: Ingredient = TestUtil.createIngredient("testing ingredient 2")

        val insertedId1 = ingredientDao.upsert(ingredient1)
        val insertedId2 = ingredientDao.upsert(ingredient2)
        ingredient1 = ingredient1.copy(id = insertedId1)
        ingredient2 = ingredient2.copy(id = insertedId2)
        var all = ingredientDao.get()

        assertEquals(ingredient1, all[0])
        assertEquals(ingredient2, all[1])

        val deleted = ingredientDao.delete(ingredient1)
        assertEquals(1, deleted)

        val newIngredientName = "test"
        val toUpdate = ingredient2.copy(name = newIngredientName)

        val updatedId = ingredientDao.upsert(toUpdate)
        assertEquals(ingredient2.id, updatedId)

        all = ingredientDao.get()
        assertEquals(newIngredientName, all[0].name)
    }

    @Test
    fun replace_on_insertDuplicate() {
        val ingredient1: Ingredient = TestUtil.createIngredient("testing step")
        val newId = ingredientDao.upsert(ingredient1)
        val ingredient2 = ingredient1.copy(id = newId)

        ingredientDao.upsert(ingredient2)

        assertEquals(1, ingredientDao.get().size)

        val step3 = ingredient2.copy(name = "Other name")

        ingredientDao.upsert(step3)
        assertEquals(1, ingredientDao.get().size)
    }

    @Test
    fun insert_on_upsertNonExisting() {
        val ingredient: Ingredient = TestUtil.createIngredient("testing ingredient").copy(id = 1)

        ingredientDao.upsert(ingredient)

        val all = ingredientDao.get()

        assertEquals(1, all.size)
    }

    @Test
    fun noAction_on_deleteNonExisting() {
        val ingredient: Ingredient = TestUtil.createIngredient("testing ingredient").copy(id = 1)

        val deleted = ingredientDao.delete(ingredient)

        assertEquals(0, deleted)
    }
}