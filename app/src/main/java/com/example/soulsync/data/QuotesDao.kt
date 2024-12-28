package com.example.soulsync.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuotesDao {
    @Query("SELECT * FROM quotes WHERE userId = :userId")
    suspend fun getQuotesForUser(userId: String): List<Quotes>

    @Query("SELECT * FROM quotes WHERE quoteId = :quoteId")
    suspend fun getQuoteById(quoteId: String): Quotes?

    @Insert
    suspend fun insertQuote(quote: Quotes)

    @Query("DELETE FROM quotes WHERE quoteId = :quoteId")
    suspend fun deleteQuoteById(quoteId: String)


    @Query("DELETE FROM quotes WHERE userId = :userId")
    suspend fun deleteAllQuotesForUser(userId: String)


}