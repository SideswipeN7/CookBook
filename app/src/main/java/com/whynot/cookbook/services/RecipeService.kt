package com.whynot.cookbook.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.whynot.cookbook.data.Recipe

class RecipeService : Service() {   // interface for clients that bind
    private var recipes: ArrayList<Recipe>? = null
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    fun GetRecipes(): List<Recipe>? = recipes

    fun refreshRecipeList(): Unit {
        recipes = ArrayList()
        recipes!!.plus(Recipe("One", null, null))
        recipes!!.plus(Recipe("Two", null, null))
        recipes!!.plus(Recipe("Three", null, null))
        recipes!!.plus(Recipe("Four", null, null))
        recipes!!.plus(Recipe("Five", null, null))
        recipes!!.plus(Recipe("Six", null, null))
        recipes!!.plus(Recipe("Seven", null, null))
        recipes!!.plus(Recipe("Eight", null, null))
        recipes!!.plus(Recipe("Nine", null, null))
        recipes!!.plus(Recipe("Ten", null, null))
        recipes!!.plus(Recipe("Eleven", null, null))
        recipes!!.plus(Recipe("Twelve", null, null))
        recipes!!.plus(Recipe("Thirteen", null, null))
        recipes!!.plus(Recipe("Fourteen", null, null))
        recipes!!.plus(Recipe("Fifteen", null, null))
    }
}
