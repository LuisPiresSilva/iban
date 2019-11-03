package net.luispiressilva.iban.di.component

import android.app.Application
import net.luispiressilva.iban.app.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import net.luispiressilva.iban.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilderModule::class,
    DatabaseModule::class,
    DialogBuilderModule::class,
    FragmentBuilderModule::class,
    UtilsModule::class,
    ReceiverModule::class,
    ServiceModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    ApiModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

    override fun inject(app: App)

}