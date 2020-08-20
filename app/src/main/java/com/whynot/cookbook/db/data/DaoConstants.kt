package com.whynot.cookbook.db.data

//CATEGORY
const val CATEGORY_TABLE: String = "CATEGORIES"
const val CATEGORY_ID: String = "CATEGORY_ID"
const val CATEGORY_NAME: String = "NAME"

//RECIPE
const val RECIPE_TABLE: String = "RECIPES"
const val RECIPE_ID: String = "RECIPE_ID"
const val RECIPE_NAME: String = "NAME"
const val RECIPE_IMAGE: String = "IMAGE"
const val RECIPE_SYNOPSIS: String = "SYNOPSIS"
const val RECIPE_REF_ID_CATEGORY: String = CATEGORY_ID

//STEP
const val STEP_TABLE: String = "STEPS"
const val STEP_ID: String = "STEP_ID"
const val STEP_NUMBER: String = "NUMBER"
const val STEP_SYNOPSIS: String = "SYNOPSIS"
const val STEP_REF_ID_RECIPE: String = RECIPE_ID

//INGREDIENTS
const val INGREDIENT_TABLE: String = "INGREDIENTS"
const val INGREDIENT_ID: String = "INGREDIENT_ID"
const val INGREDIENT_NAME: String = "INGREDIENT_NAME"
const val INGREDIENT_VALUE: String = "INGREDIENT_VALUE"
const val INGREDIENT_REF_ID_RECIPE: String = RECIPE_ID
