package com.example.asystentnauczyciela.view_model

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.example.asystentnauczyciela.model.SCDatabase
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.model.repositories.StudentRepository


class StudentViewModel(application: Application): AndroidViewModel(application) {

    val students: LiveData<List<Student>> = SCDatabase.getDatabase(application).studentDao().allStudents()
    //var studentCourses: LiveData<List<StudentCourse>> = SCDatabase.getDatabase(application).studentCourseDao().allStudentCourse()
    //var temp = SCDatabase.getDatabase(application).studentCourseDao()
    private val studentRepository:StudentRepository = StudentRepository(SCDatabase.getDatabase(application).studentDao())
    //private val studentCourseRepository: StudentCourseRepository = StudentCourseRepository(SCDatabase.getDatabase(application).studentCourseDao())

    val navigationEditStudent = MutableLiveData<Int?>()
    val navigationEditMark = MutableLiveData<Int?>()

    //var mapStudents = mutableMapOf<Int, Boolean>()

    fun addStudent(name:String, surname:String)
    {
        //musi byc wykonane asynchronicznie
        viewModelScope.launch {
            studentRepository.add(Student(id = 0,name = name, surname = surname))
            //mapStudents.put(student.id, false)
            //Log.d("nowy",mapStudents[student.id].toString())
        }
    }

    fun deleteStudent(student: Student)
    {
        viewModelScope.launch {
            studentRepository.delete(student)
            //mapStudents.remove(student.id)
        }

    }

    fun updateStudent(student: Student){
        viewModelScope.launch {
            studentRepository.update(student)
        }
    }

    fun editStudent(student: Student){
        navigationEditStudent.postValue(student.id)
    }

    fun editMark(student: Student){
        navigationEditMark.postValue(student.id)
    }


    fun onNavigationCompleted(){
        navigationEditStudent.value = null
        navigationEditMark.value = null
    }

    fun checkBeforeAddingStudent(name:String, surname: String):Boolean
    {
        if(name=="")
            return false
        else if (surname=="")
            return false
        return true
    }

    fun getStudentFromId(id: Int): Student{
        var currentStudent = Student(0,"imie - brak","nazwisko - brak")

        if(students.value !=null)
        {
            for(student in students.value!!)
            {
                if(student.id==id)
                    return student
            }
        }
        return currentStudent
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    fun makeStudentMap(): MutableMap<Int, Boolean> {
//
//        for (student in students.value!!)
//        {
//            mapStudents.put(student.id, false)
//        }
//        //studentCourses=studentCourseRepository.readAll
//        if(!studentCourses.value.isNullOrEmpty())
//        {
//            Log.d("Mapa","Jestem w robieniu mapy")
//            for (studentCourse in studentCourses.value!!)
//            {
//                mapStudents.replace(studentCourse.student_id, true)
//            }
//        }
//        return mapStudents
//    }

//    fun addToStudentCourseList(map:MutableMap<Int, Boolean>, courseId : Int, scViewModel: StudentCourseViewModel){
//
//        if(studentCourses.value.isNullOrEmpty())
//        {
//            for(item in map)
//            {
//                if (item.value)
//                {
//                    scViewModel.addStudentCourse(item.key, courseId)
//                    Log.d("spr czy null",item.key.toString())
//                    Log.d("spr czy null",courseId.toString())
//                    Log.d("spr czy null",studentCourses.value.isNullOrEmpty().toString())
//                }
//            }
//        }
//        else
//        {
//            Log.d("Nowy","Jestem w else")
//            for(item in map)
//            {
//                var isInDataBase = false
//                for (studentCourse in studentCourses.value!!)
//                {
//                    if((item.key==studentCourse.student_id) and (courseId==studentCourse.course_id) and (!item.value)) {
//                            scViewModel.deleteStudentCourse(studentCourse)
//                    }
//                    if(item.value)
//                        isInDataBase=true
//                }
//
//                if(!isInDataBase)
//                    scViewModel.addStudentCourse(item.key, courseId)
//            }
//        }
//    }

}