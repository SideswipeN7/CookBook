package com.whynot.cookbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.whynot.cookbook.adapters.RecipeAdapter
import com.whynot.cookbook.database.data.Recipe
import com.whynot.cookbook.services.RecipeService
import org.koin.android.ext.android.get


class RecipeListActivity : AppCompatActivity(),
    SwipeRefreshLayout.OnRefreshListener {
    private val cardWidth: Double = 800.0
    private val recipeService: RecipeService = get()
    private var recipes: List<Recipe>? = null
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        recyclerView = findViewById(R.id.recipe_view)
        val spans = Math.floor(
            recyclerView!!.getContext().resources
                .displayMetrics.widthPixels / cardWidth
        ).toInt()
        val manager: RecyclerView.LayoutManager = GridLayoutManager(this, spans)
        recyclerView!!.layoutManager = manager
        recyclerView!!.adapter = RecipeAdapter()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val intent = EditRecipeActivity.newIntent(this)
            startActivity(intent)
        }

        swipeRefreshLayout = findViewById(R.id.swipe_container)
        swipeRefreshLayout!!.setOnRefreshListener(this)

        onRefresh()
    }

    override fun onRefresh() {
        swipeRefreshLayout!!.isRefreshing = true
        recipeService.refresh()
        recipes = recipeService.getRecipes()
        (recyclerView!!.adapter!! as RecipeAdapter).recipeList = recipes
        swipeRefreshLayout!!.isRefreshing = false
    }
}