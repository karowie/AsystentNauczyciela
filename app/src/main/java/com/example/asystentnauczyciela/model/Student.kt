package com.example.asystentnauczyciela.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student (@PrimaryKey(autoGenerate = true) val id:Int,
                    var name:String,
                    var surname:String)