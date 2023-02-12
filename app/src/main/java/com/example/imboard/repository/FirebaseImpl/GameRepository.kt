package com.example.imboard.repository.FirebaseImpl

import com.example.imboard.data.GameDao
import com.example.imboard.remote_db.GameRemoteDataSource
import com.example.imboard.util.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val remoteDataSource: GameRemoteDataSource,
    private val localDataSource : GameDao
) {
    fun getGames() = performFetchingAndSaving(
        {localDataSource.getAllGames()},
        {remoteDataSource.getGames()},
        {localDataSource.insertGames(it.games)}
    )
}