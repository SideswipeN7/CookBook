package com.whynot.utils

import com.whynot.cookbook.db.data.*
import kotlin.random.Random

object TestUtil {
    private val random: Random = Random(0)

    fun createCategory(categoryName: String, id: Long? = null) = Category(
        id = id,
        name = "$categoryName ${getRandom()}"
    )

    fun createStep(
        synopsis: String,
        id: Long? = null,
        number: Int = 0,
        recipeId: Long? = null
    ) = Step(
        id = id,
        recipeId = recipeId,
        synopsis = synopsis,
        number = number
    )

    fun createIngredient(
        name: String,
        id: Long? = null,
        value: String = "0",
        recipeId: Long? = null
    ) = Ingredient(
        id = id,
        recipeId = recipeId,
        name = name,
        value = value
    )

    fun createRecipe(
        name: String,
        id: Long? = null,
        synopsis: String = "0",
        categoryId: Long? = null,
        image: ByteArray? = null
    ) = Recipe(
        id = id,
        synopsis = synopsis,
        name = name,
        image = image,
        categoryId = categoryId
    )

    fun joinCategoryRecipe(category1: Category, recipe: Recipe): CategoryRecipe = CategoryRecipe(
        recipeRefId = recipe.id!!,
        categoryRefId = category1.id!!
    )

    private fun getRandom(): Long = random.nextLong()
}