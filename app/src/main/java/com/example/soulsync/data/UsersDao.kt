package com.example.soulsync.data

import androidx.room.Dao
import androidx.room.Update

@Dao
interface UserDao {
    @Update
    suspend fun updateTheme(users: Users)

    @Update
    suspend fun updateNotification(users: Users)
}