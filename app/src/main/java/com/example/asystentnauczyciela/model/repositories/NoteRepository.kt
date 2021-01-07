package com.example.asystentnauczyciela.model.repositories

import androidx.lifecycle.LiveData
import com.example.asystentnauczyciela.model.Note
import com.example.asystentnauczyciela.model.NoteDao
import com.example.asystentnauczyciela.model.Student

class NoteRepository(private val noteDao: NoteDao){

    val readAll: LiveData<List<Note>> = noteDao.allNotes()

    suspend fun add(note:Note) = noteDao.insertNote(note)

    suspend fun delete(note:Note)=noteDao.deleteNote(note)

    suspend fun update(note:Note)=noteDao.updateNote(note)
}
