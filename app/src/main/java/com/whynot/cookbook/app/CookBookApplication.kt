package com.whynot.cookbook.app

import android.app.Application
import android.content.Context
import com.whynot.cookbook.RecipeListActivity
import com.whynot.cookbook.adapters.RecipeAdapter
import com.whynot.cookbook.database.DatabaseManager
import com.whynot.cookbook.services.RecipeService
import com.whynot.cookbook.viewmodels.EditRecipeViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class CookBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@CookBookApplication)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(createModule())
        }
    }

    private fun createModule(): Module {
        return module {
            single { RecipeService() }
            single { DatabaseManager(get(), "db.cookBook", null, 1) }

            viewModel { EditRecipeViewModel(get()) }
        }
    }
}
