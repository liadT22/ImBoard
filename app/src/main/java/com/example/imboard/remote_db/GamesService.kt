package com.example.imboard.remote_db

import com.example.imboard.model.AllGames
import com.example.imboard.model.Game
import retrofit2.Response
import retrofit2.http.GET

interface GamesService {
    @GET("search?order_by=rank&ascending=false&client_id=MmQcYAKi4G")
    suspend fun getAllGames() :Response<AllGames>
}