package com.example.soulsync.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemoriesDao {
    @Query("SELECT * FROM memories WHERE userId = :userId")
    suspend fun getMemoriesForUser(userId: String): List<Memories>

    @Query("SELECT * FROM memories WHERE memoryId = :memoryId")
    suspend fun getMemoryById(memoryId: String): Memories?

    @Insert
    suspend fun insertMemory(memory: Memories)

    @Query("DELETE FROM memories WHERE memoryId = :memoryId")
    suspend fun deleteMemoryById(memoryId: String)

    @Query("DELETE FROM memories WHERE userId = :userId")
    suspend fun deleteAllMemoriesForUser(userId: String)
}