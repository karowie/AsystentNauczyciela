package com.example.asystentnauczyciela.view_model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Mark
import com.example.asystentnauczyciela.model.Student
import java.text.SimpleDateFormat
import java.util.*

class ReportListAdapter(private val studentViewModel: StudentViewModel, private val courseViewModel: CourseViewModel): ListAdapter<Mark, ReportListAdapter.MarkHolder>(ReportDiff){

    inner class MarkHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkHolder {
        val view= LayoutInflater.from(parent.context)
                .inflate(R.layout.report_list_one_row,parent,false)
        return MarkHolder(view)
    }

    override fun onBindViewHolder(holder: MarkHolder, position: Int) {
        var textViewStudent = holder.itemView.findViewById<TextView>(R.id.textViewStudentReport)
        var textViewCourse = holder.itemView.findViewById<TextView>(R.id.textViewCourseReport)
        var textViewMark = holder.itemView.findViewById<TextView>(R.id.textViewMarkReport)
        var textViewNote = holder.itemView.findViewById<TextView>(R.id.textViewNoteReport)

        val mark = getItem(position)
        val student = studentViewModel.getStudentFromId(mark.student_id)
        val course = courseViewModel.getCourseFromId(mark.course_id)

        if(mark.data == SimpleDateFormat("dd-MM-yyyy").format(Date()))
        {
            textViewStudent.text = student.name + " " + student.surname
            textViewCourse.text = course.name
            textViewMark.text = mark.mark.toString()
            textViewNote.text = mark.note
        }
        else
        {
            textViewStudent.isVisible = false
            textViewCourse.isVisible = false
            textViewMark.isVisible = false
            textViewNote.isVisible = false
        }


    }

}

object ReportDiff : DiffUtil.ItemCallback<Mark>() {
    override fun areItemsTheSame(oldItem: Mark, newItem: Mark) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Mark, newItem: Mark) = oldItem == newItem

}