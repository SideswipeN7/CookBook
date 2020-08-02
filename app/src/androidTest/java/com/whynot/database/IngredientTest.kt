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
    private lateinit var IngredientDao: IngredientDao
    private lateinit var db: CookBookDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CookBookDatabase::class.java
        ).build()
        IngredientDao = db.ingredients()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeReadDeleteUpdate() {
        var ingredient1: Ingredient = TestUtil.createIngredient("testing ingredient 1")
        var ingredient2: Ingredient = TestUtil.createIngredient("testing ingredient 2")

        val insertedId1 = IngredientDao.insert(ingredient1)
        val insertedId2 = IngredientDao.insert(ingredient2)
        ingredient1 = ingredient1.copy(id = insertedId1)
        ingredient2 = ingredient2.copy(id = insertedId2)
        var all = IngredientDao.getAll()

        assertEquals(ingredient1, all[0])
        assertEquals(ingredient2, all[1])

        val deleted = IngredientDao.delete(ingredient1)
        assertEquals(1, deleted)

        val newIngredientName = "test"
        val toUpdate = ingredient2.copy(name = newIngredientName)

        val updatedIds = IngredientDao.update(toUpdate)
        assertEquals(1, updatedIds)

        all = IngredientDao.getAll()
        assertEquals(newIngredientName, all[0].name)
    }

    @Test
    fun insertDuplicate() {
        val ingredient1: Ingredient = TestUtil.createIngredient("testing step")
        val newId = IngredientDao.insert(ingredient1)
        val ingredient2 = ingredient1.copy(id = newId)

        IngredientDao.insert(ingredient2)

        assertEquals(1, IngredientDao.getAll().size)

        val step3 = ingredient2.copy(name = "Other name")

        IngredientDao.insert(step3)
        assertEquals(1, IngredientDao.getAll().size)
    }

    @Test
    fun updateNonExisting() {
        val ingredient: Ingredient = TestUtil.createIngredient("testing ingredient").copy(id = 1)

        IngredientDao.update(ingredient)

        val all = IngredientDao.getAll()

        assertEquals(0, all.size)
    }

    @Test
    fun deleteNonExisting() {
        val ingredient: Ingredient = TestUtil.createIngredient("testing ingredient").copy(id = 1)

        IngredientDao.delete(ingredient)

        val all = IngredientDao.getAll()

        assertEquals(0, all.size)
    }
}