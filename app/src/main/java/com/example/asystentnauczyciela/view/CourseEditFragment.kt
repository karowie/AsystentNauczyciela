package com.example.asystentnauczyciela.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Course
import com.example.asystentnauczyciela.model.SCDatabase
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.model.StudentCourse
import com.example.asystentnauczyciela.view_model.*
import kotlinx.android.synthetic.main.fragment_course_edit.*


const val COURSE_ID = "courseId"
private lateinit var viewModelCourse: CourseViewModel
private lateinit var viewModelStudent: StudentViewModel
private lateinit var viewModelStudentCourse: StudentCourseViewModel


class CourseEditFragment : Fragment() {

    private lateinit var addStudentAdapter: AddStudentListAdapter
    private lateinit var addStudentLayoutManager: LinearLayoutManager
    private lateinit var addStudentRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            val courseId = it.getInt(COURSE_ID)
            addStudentLayoutManager = LinearLayoutManager(context)

            viewModelStudent = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)
            viewModelCourse = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
            viewModelStudentCourse = ViewModelProvider(requireActivity()).get(StudentCourseViewModel::class.java)

            addStudentAdapter = AddStudentListAdapter(viewModelStudent.students, viewModelStudentCourse.studentCourses, courseId)

            viewModelStudent.students.observe(viewLifecycleOwner, {
                addStudentAdapter.notifyDataSetChanged()
            })

            viewModelStudentCourse.studentCourses.observe(viewLifecycleOwner, {
                addStudentAdapter.notifyDataSetChanged()
            })
        }



        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_course_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentCourse: Course

        addStudentRecyclerView=recyclerViewAddStudent.apply {
            this.layoutManager = addStudentLayoutManager
            this.adapter = addStudentAdapter
        }

        arguments?.let {
            val courseId = it.getInt(COURSE_ID)
            val currentCourse = viewModelCourse.getCourseFromId(courseId)

            editTexteditCourseName.setText(currentCourse.name)

            buttonSaveCourse.setOnClickListener {

                if(editTexteditCourseName.text != null)
                {
                    viewModelCourse.updateCourse(Course(courseId,editTexteditCourseName.text.toString()))
                    viewModelStudentCourse.addStudentToStudentCourseList(addStudentAdapter.checkedStudents, courseId)

                    //viewModelStudent.addToStudentCourseList(viewModelStudent.mapStudents, courseId, viewModelStudentCourse)
                    //Log.d("zapisz",viewModelStudent.mapStudents[viewModelStudent.students.value!![0].id].toString())
                    view.findNavController().navigate(R.id.action_courseEditFragment_to_courseFragment)
                }
                else
                {
                    Toast.makeText(context,"Uzupełnij nazwę kursu", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

}
