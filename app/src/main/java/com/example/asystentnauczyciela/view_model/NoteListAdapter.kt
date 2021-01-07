package com.example.asystentnauczyciela.view_model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Note
import com.example.asystentnauczyciela.model.Student

class NoteListAdapter(private val viewModelN: NoteViewModel): ListAdapter<Note, NoteListAdapter.NoteHolder>(NoteDiff){

    inner class NoteHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.note_list_one_row,parent,false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        var textViewNote = holder.itemView.findViewById<TextView>(R.id.textViewNoteNotes)

        val note = getItem(position)

        textViewNote.text = note.note

        val buttonDN = holder.itemView.findViewById<AppCompatImageButton>(R.id.imageButtonDelNote)
        buttonDN.setOnClickListener {
            viewModelN.deleteNote(note)
            notifyDataSetChanged()
        }
    }
}

object NoteDiff : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem

}