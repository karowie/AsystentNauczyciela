package com.example.asystentnauczyciela.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(@PrimaryKey(autoGenerate = true) val id:Int,
                var note:String)
