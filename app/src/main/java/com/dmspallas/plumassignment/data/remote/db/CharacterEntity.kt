package com.dmspallas.plumassignment.data.remote.db

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "character_table",indices = [Index(value = ["character_name"], unique = true)])
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "character_id")
    val id : Int,
    @ColumnInfo(name = "character_name")
    val name : String,
    @ColumnInfo(name = "character_description")
    val description : String,
    @ColumnInfo(name = "character_image")
    var image: String
)