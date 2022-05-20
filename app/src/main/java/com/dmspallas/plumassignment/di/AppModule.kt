package com.dmspallas.plumassignment.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dmspallas.plumassignment.data.remote.repository.CharacterService
import com.dmspallas.plumassignment.data.remote.MarvelAPI
import com.dmspallas.plumassignment.data.remote.db.CharacterDatabase
import com.dmspallas.plumassignment.data.remote.db.CharacterRepository
import com.dmspallas.plumassignment.util.PreferencesServiceImpl
import com.google.gson.Gson
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)
@Module
@InstallIn(SingletonComponent::class)
object MarvelModule {

    @Provides
    @Singleton
    fun marvelAPI(retrofit: Retrofit): MarvelAPI = retrofit.create(MarvelAPI::class.java)

    @Provides
    @Singleton
    fun retrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideRepository(
        api: MarvelAPI
    ) : CharacterService {
        return CharacterService((api))
    }
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): CharacterDatabase {
        return Room.databaseBuilder(
            app, CharacterDatabase::class.java, "marvel_db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideDao(db: CharacterDatabase) = db.dao

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context) = PreferencesServiceImpl(context)
}