package com.whynot.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.whynot.cookbook.db.CookBookDatabase
import com.whynot.cookbook.db.dao.StepDao
import com.whynot.cookbook.db.data.Step
import com.whynot.utils.TestUtil
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StepTest {
    private lateinit var stepDao: StepDao
    private lateinit var db: CookBookDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CookBookDatabase::class.java
        ).build()
        stepDao = db.steps()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeReadDeleteUpdate() {
        var step1: Step = TestUtil.createStep("testing step 1", 1)
        var step2: Step = TestUtil.createStep("testing step 2", 2)

        val insertedId1 = stepDao.insert(step1)
        val insertedId2 = stepDao.insert(step2)
        step1 = step1.copy(id = insertedId1)
        step2 = step2.copy(id = insertedId2)
        var all = stepDao.getAll()

        assertEquals(step1, all[0])
        assertEquals(step2, all[1])

        val deleted = stepDao.delete(step1)
        assertEquals(1, deleted)

        val newStepSynopsis = "test"
        val toUpdate = step2.copy(synopsis = newStepSynopsis)

        val updatedIds = stepDao.update(toUpdate)
        assertEquals(1, updatedIds)

        all = stepDao.getAll()
        assertEquals(newStepSynopsis, all[0].synopsis)
    }

    @Test
    fun insertDuplicate() {
        val step1: Step = TestUtil.createStep("testing step")
        val newId = stepDao.insert(step1)
        val step2 = step1.copy(id = newId)

        stepDao.insert(step2)

        assertEquals(1, stepDao.getAll().size)

        val step3 = step2.copy(synopsis = "Other synopsis")

        stepDao.insert(step3)
        assertEquals(1, stepDao.getAll().size)
    }

    @Test
    fun updateNonExisting() {
        val step: Step = TestUtil.createStep("testing Step").copy(id = 1)

        stepDao.update(step)

        val all = stepDao.getAll()

        assertEquals(0, all.size)
    }

    @Test
    fun deleteNonExisting() {
        val step: Step = TestUtil.createStep("testing step").copy(id = 1)

        stepDao.delete(step)

        val all = stepDao.getAll()

        assertEquals(0, all.size)
    }
}