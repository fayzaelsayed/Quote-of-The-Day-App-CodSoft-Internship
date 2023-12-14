package com.example.quoteoftheday.data.remote

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseOfQuotes(
    val count: Long,
    val totalCount: Long,
    val page: Long,
    val totalPages: Long,
    val lastItemIndex: Long,
    val results: List<Result>
)

@Serializable
data class Result(
    @SerializedName("_id")
    val id: String,

    val author: String,
    val content: String,
    val tags: List<String>,
    val authorSlug: String,
    val length: Long,
    val dateAdded: String,
    val dateModified: String
)
