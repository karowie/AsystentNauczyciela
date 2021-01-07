package com.example.asystentnauczyciela.model.repositories

import androidx.lifecycle.LiveData
import com.example.asystentnauczyciela.model.Mark
import com.example.asystentnauczyciela.model.MarkDao

class MarkRepository(private val markDao: MarkDao) {

    val readAll: LiveData<List<Mark>> = markDao.allMarks()

    suspend fun add(mark: Mark) = markDao.insertMark(mark)

    suspend fun delete(mark: Mark) = markDao.deleteMark(mark)

    suspend fun update(mark: Mark) = markDao.updateMark(mark)
}