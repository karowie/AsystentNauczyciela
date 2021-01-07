package com.example.asystentnauczyciela.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.view_model.NoteListAdapter
import com.example.asystentnauczyciela.view_model.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*

private lateinit var viewModelNote: NoteViewModel

class NoteFragment : Fragment() {

    private lateinit var noteAdapter: NoteListAdapter
    private lateinit var noteLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        noteLayoutManager = LinearLayoutManager(context)

        viewModelNote = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        noteAdapter = NoteListAdapter(viewModelNote)

        viewModelNote.notes.observe(viewLifecycleOwner, {
            noteAdapter.submitList(it)
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = RVNotes.apply {
            this.layoutManager = noteLayoutManager
            this.adapter = noteAdapter
        }

        buttonAddNote.setOnClickListener {
            view.hideKeyboard()
            if(editTextNoteFragment.text.isEmpty())
            {
                Toast.makeText(context,"Uzupełnij notatkę.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                viewModelNote.addNote(editTextNoteFragment.text.toString())
                editTextNoteFragment.setText("")
            }
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}