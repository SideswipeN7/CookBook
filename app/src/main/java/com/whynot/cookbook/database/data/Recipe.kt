package com.whynot.cookbook.database.data

import android.graphics.Bitmap

data class Recipe(
    var id: Int,
    var name: String,
    var image: Bitmap?,
    var synopsis: String,
    var categoryId: Int
)