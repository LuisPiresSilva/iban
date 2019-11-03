package net.luispiressilva.iban.di.module

import net.luispiressilva.iban.ui.dialog.MessageDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DialogBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributesMessageDialog(): MessageDialog

}