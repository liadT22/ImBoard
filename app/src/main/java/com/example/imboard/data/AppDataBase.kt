package com.example.imboard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imboard.model.Game

@Database(entities = [Game::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun gameDao() :GameDao

    companion object{
        @Volatile
        private var instance :AppDatabase? = null

        fun getDatabase(context: Context) :AppDatabase{
            return instance?: synchronized(this){
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "characters_db")
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }
}