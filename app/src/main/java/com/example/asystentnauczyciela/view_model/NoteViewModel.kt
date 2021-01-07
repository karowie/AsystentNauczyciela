package com.example.asystentnauczyciela.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.model.Note
import com.example.asystentnauczyciela.model.SCDatabase
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.model.repositories.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    val notes: LiveData<List<Note>> = SCDatabase.getDatabase(application).noteDao().allNotes()
    private val noteRepository:NoteRepository = NoteRepository(SCDatabase.getDatabase(application).noteDao())

    fun addNote(note:String)
    {
        viewModelScope.launch {
            noteRepository.add(Note(id = 0, note=note))
        }
    }


    fun deleteNote(note:Note)
    {
        viewModelScope.launch {
            noteRepository.delete(note)
        }
    }

}