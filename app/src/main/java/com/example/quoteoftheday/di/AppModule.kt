package com.example.quoteoftheday.di

import android.app.Application
import androidx.room.Room
import com.example.quoteoftheday.data.local.QuoteDatabase
import com.example.quoteoftheday.data.local.QuoteDatabaseDao
import com.example.quoteoftheday.data.remote.ApiService
import com.example.quoteoftheday.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.quotable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient.Builder = OkHttpClient.Builder()
            .hostnameVerifier { hostname, session -> true }
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        client.addInterceptor(loggingInterceptor)

        val okHttpClient = client.build()
        return okHttpClient
    }

    @Singleton
    @Provides
    fun provideRoomDatabaseBuilder(application: Application): QuoteDatabase {
        return Room.databaseBuilder(
            application,
            QuoteDatabase::class.java, "quote_db"
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideDaoInstance(quoteDatabase: QuoteDatabase): QuoteDatabaseDao {
        return quoteDatabase.quoteDatabaseDao
    }

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService, quoteDatabaseDao: QuoteDatabaseDao) : Repository {
        return Repository(apiService, quoteDatabaseDao)
    }




}