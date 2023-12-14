package com.example.quoteoftheday.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertQuoteToDatabase(quote: QuoteEntity)

    @Query("UPDATE quote_table SET quoteType = :quoteT WHERE id = :key ")
    fun updateQuoteType(key: String, quoteT: String)

    @Query("SELECT * FROM quote_table")
    fun getQuotesLiveFromDatabase(): LiveData<List<QuoteEntity>>
}