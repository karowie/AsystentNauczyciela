package com.example.asystentnauczyciela.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentCourseDao {

    @Insert
    suspend fun insertStudentCourse(studentCourse:StudentCourse)

    @Update
    suspend fun updateStudentCourse(studentCourse:StudentCourse)

    @Delete
    suspend fun deleteStudentCourse(studentCourse:StudentCourse)

    @Query("SELECT * FROM student_course_table")
    fun allStudentCourse(): LiveData<List<StudentCourse>>
}