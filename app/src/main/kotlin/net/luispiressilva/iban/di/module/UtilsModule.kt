package net.luispiressilva.iban.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import net.luispiressilva.iban.utils.manager.*
import javax.inject.Singleton

@Module
class UtilsModule {
//    @Singleton
//    @Provides
//    fun providesAccountUtils(application: Application) = AccountUtils(application)

    @Singleton
    @Provides
    fun providesIntentManager(application: Application) = CallManager(application)

    @Singleton
    @Provides
    fun providesDialogManager() = DialogManager()

    @Singleton
    @Provides
    fun providesNetworkManager() = NetworkManager()

    @Singleton
    @Provides
    fun providesKeyboardManager() = KeyboardManager()

    @Singleton
    @Provides
    fun providesPreferencesManager(application: Application) = PreferencesManager(application)
}