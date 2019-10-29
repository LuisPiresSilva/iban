package net.luispiressilva.iban.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import net.luispiressilva.iban.database.dao.GistDAO
import net.luispiressilva.iban.database.model.gist.GistEntity
import net.luispiressilva.iban.utils.helper.AppExecutors


@Database(entities = [GistEntity::class], version = 1)
abstract class IbanDatabase : RoomDatabase() {

    private var appExecutors: AppExecutors? = null

    fun initialize(appExecutors: AppExecutors) {
        this.appExecutors = appExecutors
    }

    fun runAsync(func: IbanDatabase.() -> Unit) {
        appExecutors?.getDiskIO()?.execute {
            func.invoke(this)
        }
    }

    fun deleteDatabase(isAsync : Boolean = true){
        if(isAsync) {
            runAsync {clearAllTables()}
        } else {
            clearAllTables()
        }
    }


    abstract fun getGistsDAO(): GistDAO


    companion object {
        lateinit var database: IbanDatabase


        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " + "PRIMARY KEY(`id`))")
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " + "PRIMARY KEY(`id`))")


            }
        }


    }
}