package com.whynot.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.whynot.cookbook.db.CookBookDatabase
import com.whynot.cookbook.db.dao.CategoryDao
import com.whynot.cookbook.db.data.Category
import com.whynot.utils.TestUtil
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CategoryTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var db: CookBookDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CookBookDatabase::class.java
        ).build()
        categoryDao = db.categories()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeReadDeleteUpdate() {
        val category1: Category = TestUtil.createCategory("testing category")
        val category2: Category = TestUtil.createCategory("testing category")

        val insertedIds = categoryDao.insert(category1, category2)
        var all = categoryDao.getAll()

        assertEquals(2, all.size)
        assertEquals(insertedIds[0], all[0].id)
        assertEquals(insertedIds[1], all[1].id)

        val selected = categoryDao.get(insertedIds[0])
        assertEquals(selected.id, all[0].id)
        assertEquals(selected.name, all[0].name)


        val deleted = categoryDao.delete(all[1])
        assertEquals(1, deleted)

        all = categoryDao.getAll()
        assertEquals(1, all.size)
        assertEquals(category1.name, all[0].name)

        val newCategoryName = "test"
        val toUpdate = all[0].copy(name = newCategoryName)

        val updatedIds = categoryDao.update(toUpdate)
        assertEquals(1, updatedIds)

        all = categoryDao.getAll()
        assertEquals(newCategoryName, all[0].name)
    }
}