package com.example.asystentnauczyciela.model.repositories

import androidx.lifecycle.LiveData
import com.example.asystentnauczyciela.model.StudentCourse
import com.example.asystentnauczyciela.model.StudentCourseDao

class StudentCourseRepository (private val studentCourseDao: StudentCourseDao) {

    val readAll: LiveData<List<StudentCourse>> = studentCourseDao.allStudentCourse()

    suspend fun add(studentCourse: StudentCourse) = studentCourseDao.insertStudentCourse(studentCourse)

    suspend fun delete(studentCourse: StudentCourse) = studentCourseDao.deleteStudentCourse(studentCourse)

    suspend fun update(studentCourse: StudentCourse) = studentCourseDao.updateStudentCourse(studentCourse)
}