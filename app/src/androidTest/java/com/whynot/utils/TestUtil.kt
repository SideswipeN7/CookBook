package com.whynot.utils

import com.whynot.cookbook.db.data.Category
import com.whynot.cookbook.db.data.Ingredient
import com.whynot.cookbook.db.data.Recipe
import com.whynot.cookbook.db.data.Step
import kotlin.random.Random

object TestUtil {
    private val random: Random = Random(0)

    fun createCategory(categoryName: String) = Category(
        id = null,
        name = "$categoryName ${getRandom()}"
    )

    fun createStep(synopsis: String, number: Int = 0, recipeId: Long? = null) = Step(
        id = null,
        recipeId = recipeId,
        synopsis = synopsis,
        number = number
    )

    fun createIngredient(name: String, value: String = "0", recipeId: Long? = null) = Ingredient(
        id = null,
        recipeId = recipeId,
        name = name,
        value = value
    )

    fun createRecipe(name: String, synopsis: String = "0", categoryId: Long?, image: ByteArray? = null) = Recipe(
        id = null,
        synopsis = synopsis,
        name = name,
        image = image,
        categoryId = categoryId
    )

    private fun getRandom(): Long = random.nextLong()
}