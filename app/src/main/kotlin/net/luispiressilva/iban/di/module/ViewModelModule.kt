package net.luispiressilva.iban.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.luispiressilva.iban.di.annotations.ViewModelKey
import net.luispiressilva.iban.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.luispiressilva.iban.ui.gist_detail.GistDetailViewModel
import net.luispiressilva.iban.ui.gist_list.GistListViewModel
import net.luispiressilva.iban.ui.splash.SplashViewModel

@Module(includes = [RepositoryModule::class])
abstract class ViewModelModule {
    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindsSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GistListViewModel::class)
    abstract fun bindsGistListViewModel(gistListViewModel: GistListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GistDetailViewModel::class)
    abstract fun bindsGistDetailViewModel(gistDetailViewModel: GistDetailViewModel): ViewModel

}