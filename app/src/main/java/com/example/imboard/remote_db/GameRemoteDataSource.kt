package com.example.imboard.remote_db

import javax.inject.Inject

class GameRemoteDataSource @Inject constructor(
    private val gameService: GamesService
) : BaseDataSource(){
    suspend fun  getGames() = getResult { gameService.getAllGames() }
}