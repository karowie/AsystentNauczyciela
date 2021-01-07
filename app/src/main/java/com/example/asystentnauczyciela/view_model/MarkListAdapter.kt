package com.example.asystentnauczyciela.view_model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Mark

class MarksListAdapter(private val markViewModel: MarkViewModel, private val studentID: Int, private val courseID: Int): ListAdapter<Mark, MarksListAdapter.MarkHolder>(MarksListDiff){

    inner class MarkHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.mark_one_row,parent, false)
        return MarkHolder(view)
    }

    override fun onBindViewHolder(holder: MarkHolder, position: Int) {
        var textViewMark = holder.itemView.findViewById<TextView>(R.id.textViewMark)
        var textViewNote = holder.itemView.findViewById<TextView>(R.id.textViewNote)

        val buttonDM = holder.itemView.findViewById<AppCompatImageButton>(R.id.imageButtonDelMark)
        val buttonEM = holder.itemView.findViewById<AppCompatImageButton>(R.id.imageButtonEditMark)

        val mark = getItem(position)

        if (mark in markViewModel.findMarksForSC(studentID,courseID)) {
            textViewMark.text = mark.mark.toString()
            textViewNote.text = mark.note
        }
        else{
            textViewMark.isVisible = false
            textViewNote.isVisible = false
            buttonDM.isVisible = false
            buttonEM.isVisible = false
        }

        textViewMark.text=mark.mark.toString()
        textViewNote.text=mark.note

        buttonDM.setOnClickListener{
            markViewModel.deleteMark(mark)
            notifyDataSetChanged()
        }

        buttonEM.setOnClickListener {
            markViewModel.editMark(mark)
            notifyDataSetChanged()
        }



    }
}

object MarksListDiff:DiffUtil.ItemCallback<Mark>(){
    override fun areItemsTheSame(oldItem: Mark, newItem: Mark)= oldItem === newItem

    override fun areContentsTheSame(oldItem: Mark, newItem: Mark)= oldItem == newItem
}