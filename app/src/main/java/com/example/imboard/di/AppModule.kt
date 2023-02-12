package com.example.imboard.di

import android.content.Context
import com.example.imboard.data.AppDatabase
import com.example.imboard.remote_db.GamesService
import com.example.imboard.repository.FirebaseImpl.*
import com.example.imboard.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit) : GamesService =
        retrofit.create(GamesService::class.java)


    @Provides
    @Singleton
    fun provideLocalDataBase(@ApplicationContext appContext: Context) : AppDatabase =
        AppDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideCharacterDao(database: AppDatabase) = database.gameDao()

    @Provides
    fun provideLobbiesRepository() : LobbiesRepository = LobbyRepositoryFirebase()

    @Provides
    fun provideAuthRepository() : AuthRepository = AuthRepositoryFirebase()

    @Provides
    fun provideFireBaseStorageRepository() : FireBaseStorageRepository = FireBaseStorageRepository()

}
