package com.example.asystentnauczyciela.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.view_model.*
import kotlinx.android.synthetic.main.fragment_student_mark.*
import java.text.SimpleDateFormat
import java.util.*

const val COURSE_id = "course_id"
const val student_ID = "student_id"
private lateinit var viewModelStudent: StudentViewModel
private lateinit var viewModelSC: StudentCourseViewModel
private lateinit var viewModelCourse: CourseViewModel
private lateinit var viewModelMark: MarkViewModel

class StudentMarkFragment : Fragment() {

    private lateinit var marksListAdapter: MarksListAdapter
    private lateinit var markLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            val studentID = it.getInt(student_ID)
            val courseID = it.getInt(COURSE_id)




            markLayoutManager = LinearLayoutManager(context)

            viewModelStudent = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)
            viewModelCourse = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
            viewModelSC = ViewModelProvider(requireActivity()).get(StudentCourseViewModel::class.java)
            viewModelMark = ViewModelProvider(requireActivity()).get(MarkViewModel::class.java)

            marksListAdapter = MarksListAdapter(viewModelMark, studentID, courseID)

            viewModelMark.marks.observe(viewLifecycleOwner,{
                marksListAdapter.submitList(it)
            })

            viewModelMark.navigation.observe(viewLifecycleOwner){markId: Int? ->

                markId?.let { markId:Int ->
                    var bundle = bundleOf(MARK_ID to markId)
                    findNavController().navigate(R.id.action_studentMarkFragment_to_studentMarkEditFragment, bundle)
                    viewModelMark.onNavigationCompleted()
                }
            }

        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_mark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView=RVMarks.apply {
            this.layoutManager = markLayoutManager
            this.adapter = marksListAdapter
        }

        arguments?.let {
            val studentID = it.getInt(student_ID)
            val courseID = it.getInt(COURSE_id)

            val currentStudent = viewModelStudent.getStudentFromId(studentID)
            val currentCourse = viewModelCourse.getCourseFromId(courseID)

            textViewCourseNameMark.text = currentCourse.name
            textViewStudentNameMark.text = currentStudent.name + " " + currentStudent.surname

            buttonAddMark.setOnClickListener {
                view.hideKeyboard()
                if(editTextNote.text != null)
                {
                    viewModelMark.addMark(studentID, courseID, spinnerMarks.selectedItem.toString().toDouble(), SimpleDateFormat("dd-MM-yyyy").format(Date()), editTextNote.text.toString())
                    editTextNote.setText("")
                }
                else
                {
                    Toast.makeText(context,"Uzupełnij notatkę oceny", Toast.LENGTH_SHORT).show()
                }

            }
        }


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
                    val view:TextView = super.getDropDownView(position, convertView, parent) as TextView
                    view.setTypeface(Typeface.MONOSPACE,Typeface.BOLD)
                    view.setTextColor(Color.parseColor("#FF000000"))

                    if( position==spinnerMarks.selectedItemPosition){
                        view.background= ColorDrawable(Color.parseColor("#9900693B"))
                    }
                    return view
                }

            }
            spinnerMarks.adapter=adapter
        }



    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}