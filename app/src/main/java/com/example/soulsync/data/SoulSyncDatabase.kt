package com.example.soulsync.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class, Memories::class, Quotes::class], version = 1, exportSchema = false)
abstract class SoulSyncDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun memoriesDao(): MemoriesDao
    abstract fun quotesDao(): QuotesDao

    companion object {

        @Volatile
        private var Instance: SoulSyncDatabase? = null

        fun getDatabase(context: Context): SoulSyncDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    SoulSyncDatabase::class.java,
                    "soulsync_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}