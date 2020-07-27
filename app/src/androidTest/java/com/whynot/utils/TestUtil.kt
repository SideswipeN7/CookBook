package com.whynot.utils

import com.whynot.cookbook.db.data.Category
import kotlin.random.Random

object TestUtil {
    private val random: Random = Random(0)

    fun createCategory(categoryName: String) = Category(
        id = null,
        name = "$categoryName ${getRandom()}"
    )

    private fun getRandom():Long = random.nextLong()
}