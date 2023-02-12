package com.example.imboard.model
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.net.URI

@Parcelize
@Entity(tableName = "games")
data class Game(

    @ColumnInfo(name = "name_of_game")
    val name: String,

    @ColumnInfo(name = "min_player_count")
    val min_players: Int,

    @ColumnInfo(name = "max_player_count")
    val max_players: Int,

    @ColumnInfo(name = "max_play_time")
    val max_playtime: Int,

    @ColumnInfo(name = "image")
    val image_url: String,

    @ColumnInfo(name = "description")
    val description_preview: String)

    : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var key: Int = 0

    constructor() : this( "", 0, 0, 0, "", "")
}

//@column info  - name of column in Room DB
//all the names of the variable are exactly the names that in the JSON file
// did not use @SerializedName - gson