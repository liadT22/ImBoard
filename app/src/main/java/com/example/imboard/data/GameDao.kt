package com.example.imboard.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imboard.model.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM games")
    fun getAllGames() : LiveData<List<Game>>

    @Query("SELECT * FROM games WHERE name_of_game = :name")
    fun getGame(name :String) : LiveData<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game :Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games:List<Game>)
}