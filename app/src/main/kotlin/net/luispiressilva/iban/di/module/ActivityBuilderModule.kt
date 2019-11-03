package net.luispiressilva.iban.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.luispiressilva.iban.ui.gist_detail.GistDetailActivity
import net.luispiressilva.iban.ui.gist_list.GistListActivity
import net.luispiressilva.iban.ui.splash.SplashActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributesSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributesGistListActivity(): GistListActivity

    @ContributesAndroidInjector
    abstract fun contributesGistDetailActivity(): GistDetailActivity


}