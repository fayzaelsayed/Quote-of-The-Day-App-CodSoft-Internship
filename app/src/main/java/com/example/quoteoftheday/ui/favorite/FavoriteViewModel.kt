package com.example.quoteoftheday.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteoftheday.data.local.QuoteDatabaseDao
import com.example.quoteoftheday.data.local.QuoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val quoteDatabaseDao: QuoteDatabaseDao): ViewModel() {
    fun getQuotesFromDatabase(): LiveData<List<QuoteEntity>> {
        return quoteDatabaseDao.getQuotesLiveFromDatabase()
    }

    fun updateQuoteType(key: String, quoteT: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                quoteDatabaseDao.updateQuoteType(key, quoteT)
            }catch (e: Exception){
                Log.i("updateQuoteType", "updateQuoteType: $e")
            }
        }
    }
}