package com.example.quoteoftheday.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.quoteoftheday.data.local.QuoteDatabaseDao
import com.example.quoteoftheday.data.local.QuoteEntity
import com.example.quoteoftheday.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quoteDatabaseDao: QuoteDatabaseDao,
    private val repository: Repository
) : ViewModel() {
    private val showProgress: LiveData<Boolean> = repository._showProgress
    fun getQuote() {
        viewModelScope.launch {
            repository.getQuoteFromServerToDatabase()
        }
    }

    fun getQuoteFromDatabase(): LiveData<List<QuoteEntity>> {
        return showProgress.switchMap {
            if (!it) {
                quoteDatabaseDao.getQuotesLiveFromDatabase()
            } else {
                null
            }
        }
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