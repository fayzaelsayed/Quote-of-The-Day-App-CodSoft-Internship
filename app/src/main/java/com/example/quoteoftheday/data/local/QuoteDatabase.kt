package com.example.quoteoftheday.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [QuoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(QuoteTypeConverter::class)
abstract class QuoteDatabase : RoomDatabase() {
    abstract val quoteDatabaseDao: QuoteDatabaseDao
}