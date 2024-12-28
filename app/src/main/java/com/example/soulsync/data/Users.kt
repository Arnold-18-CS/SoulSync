package com.example.soulsync.data

import androidx.room.Entity

@Entity(tableName = "users", primaryKeys = ["userId"])
data class Users(
    var userId: String,
    var email : String,
    var bgColor: String,
    var textColor: String,
    var iconColor: String,
    var componentColor: String,
    var notificationEnable: Boolean
)
