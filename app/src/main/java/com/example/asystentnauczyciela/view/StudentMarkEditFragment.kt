package com.example.asystentnauczyciela.view

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Course
import com.example.asystentnauczyciela.model.Mark
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.view_model.*
import kotlinx.android.synthetic.main.fragment_student_mark.*
import kotlinx.android.synthetic.main.fragment_student_mark_edit.*
import java.text.SimpleDateFormat
import java.util.*


const val MARK_ID = "mark_id"
private lateinit var viewModelCourse: CourseViewModel
private lateinit var viewModelStudent: StudentViewModel
private lateinit var viewModelMark: MarkViewModel

class StudentMarkEditFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

            viewModelStudent = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)
            viewModelCourse = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
            viewModelMark = ViewModelProvider(requireActivity()).get(MarkViewModel::class.java)


            viewModelMark.marks.observe(viewLifecycleOwner, {

            })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_mark_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {context->
            val list = mutableListOf(
                "5.0",
                "4.5",
                "4.0",
                "3.5",
                "3.0",
                "2.0"
            )
            val adapter: ArrayAdapter<String> = object: ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_dropdown_item,
                list
            ){
                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                    view.setTypeface(Typeface.MONOSPACE, Typeface.BOLD)
                    view.setTextColor(Color.parseColor("#FF000000"))

                    if( position==spinnerMarksEdit.selectedItemPosition){
                        view.background= ColorDrawable(Color.parseColor("#9900693B"))
                    }
                    return view
                }

            }
            spinnerMarksEdit.adapter=adapter
        }

        arguments?.let {
            val markId = it.getInt(MARK_ID)

            var currentStudent: Student
            var currentCourse: Course
            var currentMark = viewModelMark.getMarkFromId(markId)

            currentStudent = viewModelStudent.getStudentFromId(currentMark.student_id)
            currentCourse = viewModelCourse.getCourseFromId(currentMark.course_id)
            editTextNoteEdit.setText(currentMark.note)
            textViewStudentNameMarkEdit.text = currentStudent.name + " " + currentStudent.surname
            textViewCourseNameMarkEdit.text = currentCourse.name
            var spinerIndex = viewModelMark.changeMarkToSpiner(currentMark.mark)
            spinnerMarksEdit.setSelection(spinerIndex)




            buttonSaveMark.setOnClickListener {
                viewModelMark.updateMark(Mark(currentMark.id, currentMark.student_id, currentMark.course_id,spinnerMarksEdit.selectedItem.toString().toDouble(), SimpleDateFormat("dd-MM-yyyy").format(Date()),editTextNoteEdit.text.toString()))

                val bundle = bundleOf(COURSE_id to currentMark.course_id, student_ID to currentMark.student_id)
                view.findNavController().navigate(R.id.action_studentMarkEditFragment_to_studentMarkFragment, bundle)
            }
        }

    }

}