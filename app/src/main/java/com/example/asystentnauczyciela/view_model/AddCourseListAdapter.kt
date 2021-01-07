package com.example.asystentnauczyciela.view_model

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Course
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.model.StudentCourse

class AddCourseListAdapter (var courses: LiveData<List<Course>>,
                            var connections: LiveData<List<StudentCourse>>,
                            var studentId:Int): RecyclerView.Adapter<AddCourseListAdapter.CourseHolder>() {

    var checkedCourses = mutableMapOf<Int, Boolean>()
    inner class CourseHolder(view: View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_course_one_row, parent, false)
        return CourseHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CourseHolder, position: Int) {

        var checkBoxC= holder.itemView.findViewById<CheckBox>(R.id.checkBoxCourse)
        checkBoxC.text = courses.value?.get(position)?.name

        val thisStudenCourse = connections.value?.find { x -> x.course_id==courses.value?.get(position)?.id && x.student_id==studentId}

        checkBoxC.isChecked = thisStudenCourse != null

        val courseID = courses.value!!.get(position).id

        checkedCourses.put(courseID, checkBoxC.isChecked)

        checkBoxC.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                checkedCourses.replace(courseID, true)
            } else {
                checkedCourses.replace(courseID, false)
            }

        }
    }

    override fun getItemCount(): Int = courses.value?.size ?: 0

}

