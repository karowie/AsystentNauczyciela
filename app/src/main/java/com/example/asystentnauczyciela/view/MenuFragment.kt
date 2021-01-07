package com.example.asystentnauczyciela.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.asystentnauczyciela.R
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonCourses.setOnClickListener { view->view.findNavController().navigate(R.id.action_menuFragment_to_courseFragment)}
        buttonStudents.setOnClickListener { view->view.findNavController().navigate(R.id.action_menuFragment_to_studentFragment)}
        buttonReport.setOnClickListener { view->view.findNavController().navigate(R.id.action_menuFragment_to_reportFragment)}
        buttonNotes.setOnClickListener { view -> view.findNavController().navigate(R.id.action_menuFragment_to_noteFragment) }
    }


}