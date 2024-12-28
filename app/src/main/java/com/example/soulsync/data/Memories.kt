package com.example.soulsync.data

import androidx.room.Entity
import java.security.Timestamp

@Entity(tableName = "memories", primaryKeys = ["memoryId"])
data class Memories(
    var memoryId: String,
    var userId: String,
    var title: String,
    var content: String,
    var timestamp: Timestamp
)
