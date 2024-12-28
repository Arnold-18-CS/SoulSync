package com.example.soulsync.data

import androidx.room.Entity
import java.sql.Timestamp

@Entity(tableName = "quotes", primaryKeys = ["quoteId"])
data class Quotes(
    var quoteId: String,
    var userId: String,
    var quote: String,
    var author: String,
    var timestamp: Timestamp
)
