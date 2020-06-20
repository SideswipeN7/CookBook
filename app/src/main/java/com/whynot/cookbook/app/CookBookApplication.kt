package com.whynot.cookbook.app

import android.app.Application
import com.whynot.cookbook.RecipeListActivity
import com.whynot.cookbook.services.RecipeService
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
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
            modules(listOf(
                module {
                    scope<RecipeService> { RecipeService() }
                    scope<RecipeListActivity> { RecipeListActivity(get()) }
                }
            ))
        }
    }
}
