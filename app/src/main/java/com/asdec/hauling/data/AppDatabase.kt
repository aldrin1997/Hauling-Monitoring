package com.asdec.hauling.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@Database(
    entities = [Batch::class, Truck::class, Photo::class, User::class],
    version = 4, // Incremented version because the User schema changed
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hauling_database"
                )
                    .addCallback(AppDatabaseCallback())
                    .fallbackToDestructiveMigration() // Wipes old data to apply new schema
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val userDao = database.userDao()

                        // FIX: Added 'id' to match the updated User data class
                        val admin = User(
                            id = UUID.randomUUID().toString(), // Generates the missing 'id'
                            username = "admin",
                            password = "password123",
                            fullName = "System Administrator",
                            role = "ADMIN"
                        )
                        userDao.createUser(admin)
                    }
                }
            }
        }
    }
}