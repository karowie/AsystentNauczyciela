package com.example.asystentnauczyciela.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.view_model.StudentListAdapter
import com.example.asystentnauczyciela.view_model.StudentViewModel
import kotlinx.android.synthetic.main.fragment_student.*


private lateinit var viewModelStudent: StudentViewModel

class StudentFragment : Fragment() {


    private lateinit var studentAdapter: StudentListAdapter
    private lateinit var studentLayoutManager: LinearLayoutManager
    private lateinit var studentRecyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        studentLayoutManager = LinearLayoutManager(context)

        viewModelStudent = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)

        studentAdapter = StudentListAdapter(viewModelStudent)

        viewModelStudent.students.observe(viewLifecycleOwner, {
            studentAdapter.submitList(it)
        })

        viewModelStudent.navigationEditStudent.observe(viewLifecycleOwner) { studentId: Int? ->

            studentId?.let { studentId: Int ->
                val bundle = bundleOf(STUDENT_ID to studentId)
                findNavController().navigate(R.id.action_studentFragment_to_studentEditFragment, bundle)
                viewModelStudent.onNavigationCompleted()
            }
        }

        return inflater.inflate(R.layout.fragment_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentRecyclerView=StudentList.apply {
            this.layoutManager = studentLayoutManager
            this.adapter = studentAdapter
        }

        buttonAddStudent.setOnClickListener{
            view.hideKeyboard()
            if(!viewModelStudent.checkBeforeAddingStudent(editTextStudentName.text.toString(), editTextStudentSurname.text.toString()))
            {
                Toast.makeText(context,"Uzupełnij wszytskie pola", Toast.LENGTH_SHORT).show()
            }
            else
            {
                viewModelStudent.addStudent(editTextStudentName.text.toString(), editTextStudentSurname.text.toString())
                editTextStudentName.setText("")
                editTextStudentSurname.setText("")
            }
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}