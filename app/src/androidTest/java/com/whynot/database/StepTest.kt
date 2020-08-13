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
    fun correctFlow_on_writeReadDeleteUpdate() {
        var step1: Step = TestUtil.createStep("testing step 1", 1)
        var step2: Step = TestUtil.createStep("testing step 2", 2)

        val insertedId1 = stepDao.upsert(step1)
        val insertedId2 = stepDao.upsert(step2)
        step1 = step1.copy(id = insertedId1)
        step2 = step2.copy(id = insertedId2)
        var all = stepDao.get()

        assertEquals(step1, all[0])
        assertEquals(step2, all[1])

        val deleted = stepDao.delete(step1)
        assertEquals(1, deleted)

        val newStepSynopsis = "test"
        val toUpdate = step2.copy(synopsis = newStepSynopsis)

        val updatedId = stepDao.upsert(toUpdate)
        all = stepDao.get()
        assertEquals(step2.id, updatedId)
        assertEquals(1, all.size)

        all = stepDao.get()
        assertEquals(newStepSynopsis, all[0].synopsis)
    }

    @Test
    fun replace_on_insertDuplicate() {
        val step1: Step = TestUtil.createStep("testing step")
        val newId = stepDao.upsert(step1)
        val step2 = step1.copy(id = newId)

        stepDao.upsert(step2)

        assertEquals(1, stepDao.get().size)

        val step3 = step2.copy(synopsis = "Other synopsis")

        stepDao.upsert(step3)
        assertEquals(1, stepDao.get().size)
    }

    @Test
    fun insert_on_updateNonExisting() {
        val step: Step = TestUtil.createStep("testing Step").copy(id = 1)

        val updated = stepDao.upsert(step)

        val all = stepDao.get()

        assertEquals(1, all.size)
        assertEquals(1, updated)
    }

    @Test
    fun noAction_on_deleteNonExisting() {
        val step: Step = TestUtil.createStep("testing step").copy(id = 1)

        val deleted = stepDao.delete(step)

        assertEquals(0, deleted)
    }
}