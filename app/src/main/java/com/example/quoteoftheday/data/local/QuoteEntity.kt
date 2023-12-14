package com.example.quoteoftheday.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quoteoftheday.data.remote.Result

@Entity(tableName = "quote_table")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val quoteType: String,
    val author: String,
    val content: String,
    val tags: List<String>,
    val authorSlug: String,
    val length: Long,
    val dateAdded: String,
    val dateModified: String
)
