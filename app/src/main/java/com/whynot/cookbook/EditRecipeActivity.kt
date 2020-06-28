package com.whynot.cookbook

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.whynot.cookbook.database.data.Recipe
import com.whynot.cookbook.databinding.ActivityEditRecipeBinding
import com.whynot.cookbook.viewmodels.EditRecipeViewModel
import kotlinx.android.synthetic.main.activity_edit_recipe.*
import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditRecipeActivity : AppCompatActivity() {
    private val viewModel: EditRecipeViewModel by viewModel()

    companion object {
        private const val EXTRA: String = "RECIPE_ID"
        private const val NO_ID: Int = -1

        fun newIntent(context: Context): Intent {
            return Intent(context, EditRecipeActivity::class.java)
        }

        fun newIntent(context: Context, recipe: Recipe): Intent {
            val intent = newIntent(context)
            intent.putExtra(EXTRA, recipe.id)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityEditRecipeBinding = setContentView(this, R.layout.activity_edit_recipe)
//        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()


        val recipeId = intent.getIntExtra(EXTRA, NO_ID)
        viewModel.clear()
        if (recipeId != NO_ID) {
            viewModel.loadRecipe(recipeId)
        }

        btn_save.setOnClickListener{
            viewModel.saveRecipe()
            this.finish()
        }

        btn_cancel.setOnClickListener{
            this.finish()
        }

    }




}