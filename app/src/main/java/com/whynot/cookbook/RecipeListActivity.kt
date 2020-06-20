package com.whynot.cookbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.whynot.cookbook.adapters.RecipeAdapter
import com.whynot.cookbook.data.Recipe
import com.whynot.cookbook.services.RecipeService


class RecipeListActivity(private val recipeService: RecipeService) : AppCompatActivity(),
    SwipeRefreshLayout.OnRefreshListener {
    private var recipes: List<Recipe>? = null
    private var recyclerView: RecyclerView? = null
    private var recipeAdapter: RecipeAdapter? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        recyclerView = findViewById(R.id.recipe_view)
        val manager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = manager
        recyclerView!!.adapter = recipeAdapter




        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        swipeRefreshLayout = findViewById(R.id.swipe_container)
    }

    override fun onRefresh() {
        swipeRefreshLayout!!.isRefreshing = true
        recipeService.refreshRecipeList()
        recipes = recipeService.GetRecipes()
        recipeAdapter = RecipeAdapter(recipes!!)
        swipeRefreshLayout!!.isRefreshing = false
    }
}