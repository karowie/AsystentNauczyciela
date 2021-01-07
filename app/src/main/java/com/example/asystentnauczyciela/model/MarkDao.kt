package com.example.asystentnauczyciela.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MarkDao {

    @Insert
    suspend fun insertMark(mark:Mark)

    @Update
    suspend fun updateMark(mark:Mark)

    @Delete
    suspend fun deleteMark(mark:Mark)

    @Query("SELECT * FROM mark_table")
    fun allMarks(): LiveData<List<Mark>>
}