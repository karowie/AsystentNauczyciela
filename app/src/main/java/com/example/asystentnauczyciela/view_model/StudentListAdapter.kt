package com.example.asystentnauczyciela.view_model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Student
import androidx.recyclerview.widget.ListAdapter
import com.example.asystentnauczyciela.model.Course

class StudentListAdapter(private val viewModelS: StudentViewModel): ListAdapter<Student, StudentListAdapter.StudentHolder>(StudentDiff) {

    inner class StudentHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_one_row,parent,false)
        return StudentHolder(view)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        var textViewName = holder.itemView.findViewById<TextView>(R.id.textViewName)
        var textViewSurname = holder.itemView.findViewById<TextView>(R.id.textViewSurname)

        val student = getItem(position)

        textViewName.text=student.name
        textViewSurname.text=student.surname

        val buttonD = holder.itemView.findViewById<AppCompatImageButton>(R.id.imageButtonDelStudent)
        buttonD.setOnClickListener {
            viewModelS.deleteStudent(student)
            notifyDataSetChanged()
        }

        val buttonE = holder.itemView.findViewById<AppCompatImageButton>(R.id.imageButtonEditStudent)
        buttonE.setOnClickListener {
            viewModelS.editStudent(student)
        }
    }
}

object StudentDiff : DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Student, newItem: Student) = oldItem == newItem

}