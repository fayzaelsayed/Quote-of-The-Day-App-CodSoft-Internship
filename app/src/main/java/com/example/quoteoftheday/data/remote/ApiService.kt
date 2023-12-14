package com.example.quoteoftheday.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("quotes")
    suspend fun getQuoteOfTheDay(): Response<ResponseOfQuotes>
}