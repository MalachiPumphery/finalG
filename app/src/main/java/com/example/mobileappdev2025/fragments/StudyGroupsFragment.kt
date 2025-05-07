package com.example.mobileappdev2025.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileappdev2025.R
import com.example.mobileappdev2025.adapters.StudyGroupsAdapter
import com.example.mobileappdev2025.models.StudyGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudyGroupsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: StudyGroupsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_study_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        recyclerView = view.findViewById(R.id.groups_recycler_view)
        fab = view.findViewById(R.id.fab_add_group)
        
        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = StudyGroupsAdapter(
            onGroupClick = { group ->
                // TODO: Navigate to group details
                Toast.makeText(context, "Selected: ${group.name}", Toast.LENGTH_SHORT).show()
            }
        )
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@StudyGroupsFragment.adapter
        }

        // TODO: Load groups from Firestore
        // For now, show some dummy data
        val dummyGroups = listOf(
            StudyGroup(
                id = "1",
                name = "Math Study Group",
                description = "Weekly study sessions for Calculus II",
                courseId = "MATH201"
            ),
            StudyGroup(
                id = "2",
                name = "Programming Club",
                description = "Learn and practice programming together",
                courseId = "CS101"
            )
        )
        adapter.updateGroups(dummyGroups)
    }

    private fun setupFab() {
        fab.setOnClickListener {
            // TODO: Show create group dialog
            Toast.makeText(context, "Create group functionality coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
} 