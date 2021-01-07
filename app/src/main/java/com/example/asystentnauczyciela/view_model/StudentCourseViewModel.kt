package com.example.asystentnauczyciela.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.model.SCDatabase
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.model.StudentCourse
import com.example.asystentnauczyciela.model.repositories.StudentCourseRepository
import kotlinx.coroutines.launch

class StudentCourseViewModel(application: Application): AndroidViewModel(application) {

    var studentCourses: LiveData<List<StudentCourse>>
    private val studentCourseRepository: StudentCourseRepository
    //var mapStudents = mutableMapOf<Int, Boolean>()

    init{
        studentCourses = SCDatabase.getDatabase(application).studentCourseDao().allStudentCourse()
        studentCourseRepository = StudentCourseRepository(SCDatabase.getDatabase(application).studentCourseDao())
    }

    fun addStudentCourse(student_id: Int, course_id: Int)
    {
        viewModelScope.launch {
            studentCourseRepository.add(StudentCourse(0, student_id, course_id))
        }
    }

    fun deleteStudentCourse(studentCourse:StudentCourse)
    {
        viewModelScope.launch {
            studentCourseRepository.delete(studentCourse)
        }
    }

    fun updateStudentCourse(studentCourse:StudentCourse)
    {
        viewModelScope.launch {
            studentCourseRepository.update(studentCourse)
        }
    }

    fun isConnectionInDB(idStudent:Int, idCourse: Int): Boolean {
        var value = false
        for (sc in studentCourses.value!!) {
            if (idStudent == sc.student_id && idCourse == sc.course_id) {
                value = true
                return value

            }
        }
        return value
    }

    fun addStudentToStudentCourseList(map: MutableMap<Int,Boolean>, courseID: Int){

        for (maps in map) {

            if (studentCourses.value.isNullOrEmpty()) {
                if(maps.value) {
                    addStudentCourse(maps.key, courseID)
                }
            }
            else {
                if (!maps.value) {
                    if(isConnectionInDB(maps.key, courseID)){
                        val thisSC = studentCourses.value?.find {
                            x -> x.course_id == courseID && x.student_id == maps.key }
                        deleteStudentCourse(thisSC!!)
                    }
                }
                else{
                    if(!isConnectionInDB(maps.key, courseID)){
                        addStudentCourse(maps.key, courseID)

                    }
                }
            }


        }

    }

    fun addCourseToStudentCourseList(map: MutableMap<Int,Boolean>, studentID: Int){

        for (maps in map) {

            if (studentCourses.value.isNullOrEmpty()) {
                if(maps.value) {
                    addStudentCourse( studentID, maps.key)
                }
            }
            else {

                if (!maps.value) {
                    if(isConnectionInDB(studentID, maps.key)){
                        val thisSC = studentCourses.value?.find {
                            x -> x.course_id == maps.key && x.student_id == studentID }
                        deleteStudentCourse(thisSC!!)
                    }
                }
                else{
                    if(!isConnectionInDB(studentID, maps.key)){
                        addStudentCourse( studentID, maps.key)

                    }
                }

            }

        }

    }

    fun getStudentsinCourse(students: LiveData<List<Student>>, courseId: Int):List<Student>
    {
        var list = mutableListOf<Student>()

        for(student in students.value!!)
        {
            for(sc in studentCourses.value!!)
            {
                if((sc.student_id==student.id)&&(sc.course_id==courseId))
                {
                    list.add(student)
                }
            }
        }

        return list
    }

}