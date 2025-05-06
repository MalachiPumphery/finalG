package com.example.mobileappdev2025.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileappdev2025.R
import com.example.mobileappdev2025.adapters.CourseAdapter
import com.example.mobileappdev2025.models.Course
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CoursesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addCourseFab: FloatingActionButton
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_courses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.courses_recycler_view)
        addCourseFab = view.findViewById(R.id.add_course_fab)

        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        courseAdapter = CourseAdapter(mutableListOf())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = courseAdapter
        }
        // TODO: Load courses from database
    }

    private fun setupFab() {
        addCourseFab.setOnClickListener {
            // TODO: Show add course dialog
        }
    }
} 