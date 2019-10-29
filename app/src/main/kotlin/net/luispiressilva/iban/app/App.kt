package net.luispiressilva.iban.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.android.*
import net.luispiressilva.iban.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject
import net.luispiressilva.iban.di.component.DaggerAppComponent


/**
 * Base class fot the application.
 * You need to add this class to your manifest file.
 */
open class App @Inject constructor() : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = androidInjector

//    @Inject
//    lateinit var database: DreamwiseDatabase

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

//        database.deleteDatabase()

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

}
