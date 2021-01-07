package com.example.asystentnauczyciela.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.model.Course
import com.example.asystentnauczyciela.model.Mark
import com.example.asystentnauczyciela.model.SCDatabase
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.model.repositories.MarkRepository
import kotlinx.coroutines.launch
import java.util.*

class MarkViewModel(application: Application): AndroidViewModel(application) {

    val marks: LiveData<List<Mark>>
    private val markRepository: MarkRepository

    val navigation = MutableLiveData<Int?>()

    init{
        marks = SCDatabase.getDatabase(application).markDao().allMarks()
        markRepository = MarkRepository(SCDatabase.getDatabase(application).markDao())
    }

    fun addMark(student_id: Int, course_id: Int, mark: Double, data: String, note: String)
    {
        viewModelScope.launch {
            markRepository.add(Mark(0, student_id, course_id,mark,data,note))
        }
    }

    fun deleteMark(mark: Mark)
    {
        viewModelScope.launch {
            markRepository.delete(mark)
        }
    }

    fun updateMark(mark: Mark)
    {
        viewModelScope.launch {
            markRepository.update(mark)
        }
    }

    fun findMarksForSC(studentID: Int, courseID: Int):List<Mark>{
        var markList = mutableListOf<Mark>()
        if (marks.value != null)
            for (mark in marks.value!!){
                if(mark.course_id == courseID && mark.student_id==studentID){
                    markList.add(mark)
                }
            }
        return markList
    }

    fun editMark(mark: Mark) {
        navigation.postValue(mark.id)
    }


    fun onNavigationCompleted() {
        navigation.value = null
    }

    fun changeMarkToSpiner(mark:Double):Int{
        var index = 0

        when(mark) {
            5.0 -> index = 0
            4.5 -> index = 1
            4.0 -> index = 2
            3.5 -> index = 3
            3.0 -> index = 4
            2.0 -> index = 5
        }

        return index
    }

    fun getMarkFromId(id: Int): Mark {
        var currentMark = Mark(0,0,0,0.0,"","")

        if(marks.value !=null)
        {
            for(mark in marks.value!!)
            {
                if(mark.id==id)
                    return mark
            }
        }
        return currentMark
    }


}