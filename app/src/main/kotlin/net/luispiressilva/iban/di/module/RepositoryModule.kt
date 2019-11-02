package net.luispiressilva.iban.di.module

import dagger.Binds
import dagger.Module
import net.luispiressilva.iban.data.repository.gist.GistRepositoryImpl
import net.luispiressilva.iban.data.repository.gist.GistReposity

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsGistRepository(gistRepositoryImpl: GistRepositoryImpl): GistReposity


}