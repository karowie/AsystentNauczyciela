package com.example.asystentnauczyciela.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Course
import com.example.asystentnauczyciela.model.SCDatabase
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.view_model.*
import kotlinx.android.synthetic.main.fragment_student_list.*

const val COURSEID = "courseId"
private lateinit var viewModelCourse: CourseViewModel
private lateinit var viewModelStudent: StudentViewModel
private lateinit var viewModelStudentCourse: StudentCourseViewModel
private lateinit var viewModelMark: MarkViewModel

class StudentListFragment : Fragment() {

    private lateinit var studentsInCourseAdapter: StudentsInCourseAdapter
    private lateinit var LayoutManager: LinearLayoutManager
    private lateinit var RecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            val courseId = it.getInt(COURSEID)
            LayoutManager = LinearLayoutManager(context)

            viewModelStudent = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)
            viewModelCourse = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
            viewModelStudentCourse = ViewModelProvider(requireActivity()).get(StudentCourseViewModel::class.java)
            viewModelMark = ViewModelProvider(requireActivity()).get(MarkViewModel::class.java)

            studentsInCourseAdapter = StudentsInCourseAdapter(viewModelStudent, viewModelStudentCourse.studentCourses, courseId)

            viewModelStudent.students.observe(viewLifecycleOwner, {
                studentsInCourseAdapter.submitList(it)
            })

            viewModelStudentCourse.studentCourses.observe(viewLifecycleOwner, {

            })

            viewModelMark.marks.observe(viewLifecycleOwner, {

            })

//            var list = viewModelStudentCourse.getStudentsinCourse(viewModelStudent.students, courseId)
//
//            viewModelStudent.students.observe(viewLifecycleOwner,{
//
//                studentsInCourseAdapter.submitList(list)
//            })

            viewModelStudent.navigationEditMark.observe(viewLifecycleOwner) { studentId: Int? ->
                studentId?.let { studentId: Int ->
                    val bundle = bundleOf(student_ID to studentId, COURSE_id to courseId)
                    findNavController().navigate(R.id.action_studentListFragment_to_studentMarkFragment, bundle)
                    viewModelStudent.onNavigationCompleted()
                }
            }

            }
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_student_list, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            //var currentCourse: Course

            RecyclerView = RVStudentList.apply {
                this.layoutManager = LayoutManager
                this.adapter = studentsInCourseAdapter
            }

//        arguments?.let {
//            val courseId = it.getInt(COURSEID)
//
//        }
        }
    }
