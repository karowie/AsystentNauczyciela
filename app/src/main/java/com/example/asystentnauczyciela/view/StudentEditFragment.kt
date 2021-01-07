package com.example.asystentnauczyciela.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.view_model.*
import kotlinx.android.synthetic.main.fragment_course_edit.*
import kotlinx.android.synthetic.main.fragment_student.*
import kotlinx.android.synthetic.main.fragment_student_edit.*

const val STUDENT_ID = "studentId"
private lateinit var viewModelStudent: StudentViewModel
private lateinit var viewModelCourse: CourseViewModel
private lateinit var viewModelStudentCourse: StudentCourseViewModel

class StudentEditFragment : Fragment() {

    private lateinit var addCourseAdapter: AddCourseListAdapter
    private lateinit var addCourseLayoutManager: LinearLayoutManager
    private lateinit var addCourseRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            val studentId = it.getInt(STUDENT_ID)
            addCourseLayoutManager = LinearLayoutManager(context)

            viewModelStudent = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)
            viewModelCourse = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
            viewModelStudentCourse = ViewModelProvider(requireActivity()).get(StudentCourseViewModel::class.java)

            addCourseAdapter = AddCourseListAdapter(viewModelCourse.courses, viewModelStudentCourse.studentCourses, studentId)

            viewModelCourse.courses.observe(viewLifecycleOwner, {
                addCourseAdapter.notifyDataSetChanged()
            })

            viewModelStudentCourse.studentCourses.observe(viewLifecycleOwner, {
                addCourseAdapter.notifyDataSetChanged()
            })
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addCourseRecyclerView = recyclerViewAddCourse.apply {
            this.layoutManager = addCourseLayoutManager
            this.adapter = addCourseAdapter
        }

        arguments?.let {
            val studentId = it.getInt(STUDENT_ID)

            val currentStudent = viewModelStudent.getStudentFromId(studentId)

            editTexteditStudentName.setText(currentStudent.name)
            editTexteditStudentSurname.setText(currentStudent.surname)

            buttonSaveStudent.setOnClickListener {
                if(editTexteditStudentName.text!=null && editTexteditStudentSurname.text != null) {
                    viewModelStudent.updateStudent(
                        Student(
                            studentId,
                            editTexteditStudentName.text.toString(),
                            editTexteditStudentSurname.text.toString()
                        )
                    )
                    viewModelStudentCourse.addCourseToStudentCourseList(
                        addCourseAdapter.checkedCourses,
                        studentId
                    )
                    view.findNavController()
                        .navigate(R.id.action_studentEditFragment_to_studentFragment)
                }
                else
                {
                    Toast.makeText(context,"Uzupełnij wszytskie pola", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}