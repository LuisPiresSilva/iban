package net.luispiressilva.iban.di.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import net.luispiressilva.iban.utils.helper.AppExecutors
import dagger.Module
import dagger.Provides
import net.luispiressilva.iban.database.IbanDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    companion object {
        const val DATABASE_NAME = "iban_db"

    }


    @Singleton
    @Provides
    fun provideDatabase(application: Application, appExecutors: AppExecutors): IbanDatabase {
        IbanDatabase.database = Room
                .databaseBuilder(application, IbanDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        // do something after database has been created
//                        Executors.newSingleThreadScheduledExecutor().execute {
//                            //RUN IN BACKGROUND
//
//                        }

                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        // do something every time database is open
//                        Executors.newSingleThreadScheduledExecutor().execute {
//                            //RUN IN BACKGROUND
//
//                        }
                    }
                })
//                .addMigrations(
//                        IbanDatabase.MIGRATION_2_3,
//                        IbanDatabase.MIGRATION_1_2
//                )
                .fallbackToDestructiveMigration()
                .build()

        IbanDatabase.database.initialize(appExecutors)

        return IbanDatabase.database
    }


}