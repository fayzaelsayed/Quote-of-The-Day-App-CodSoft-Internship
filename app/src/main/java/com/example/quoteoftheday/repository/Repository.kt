package com.example.quoteoftheday.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.quoteoftheday.data.local.QuoteDatabaseDao
import com.example.quoteoftheday.data.local.QuoteEntity
import com.example.quoteoftheday.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val quoteDatabaseDao: QuoteDatabaseDao
) {
    val _showProgress = MutableLiveData(true)
    suspend fun getQuoteFromServerToDatabase() {
        try {
            withContext(Dispatchers.IO) {
                val response =
                    apiService.getQuoteOfTheDay()
                if (response.isSuccessful) {
                    val responseDetails = response.body()

                    responseDetails?.let {
                        for (quote in responseDetails.results) {
                            val entity = QuoteEntity(
                                id = quote.id,
                                quoteType = "REGULAR",
                                author = quote.author,
                                content = quote.content,
                                tags = quote.tags,
                                authorSlug = quote.authorSlug,
                                length = quote.length,
                                dateAdded = quote.dateAdded,
                                dateModified = quote.dateModified
                            )
                            quoteDatabaseDao.insertQuoteToDatabase(entity)
                            _showProgress.postValue(false)
                        }
                    }
                } else {
                    Log.i("TAG", "getQuoteFromServerToDatabase: ${response.body()}")
                    _showProgress.postValue(false)
                }
            }
        }catch (e:Exception){
            Log.i("error", "getQuoteFromServerToDatabase: $e")
        }

    }
}