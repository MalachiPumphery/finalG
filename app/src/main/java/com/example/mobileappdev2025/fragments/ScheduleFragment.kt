package com.example.mobileappdev2025.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileappdev2025.R
import com.example.mobileappdev2025.adapters.EventsAdapter
import com.example.mobileappdev2025.models.Event
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ScheduleFragment : Fragment() {
    private lateinit var calendarView: CalendarView
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        calendarView = view.findViewById(R.id.calendar_view)
        recyclerView = view.findViewById(R.id.events_recycler_view)
        fab = view.findViewById(R.id.fab_add_event)
        
        setupCalendar()
        setupRecyclerView()
        setupFab()
    }

    private fun setupCalendar() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            // TODO: Load events for selected date
            Toast.makeText(context, "Loading events for ${month + 1}/$dayOfMonth/$year", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = EventsAdapter(
            onEventClick = { event ->
                // TODO: Show event details
                Toast.makeText(context, "Selected: ${event.title}", Toast.LENGTH_SHORT).show()
            }
        )
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@ScheduleFragment.adapter
        }

        // TODO: Load events from Firestore
        // For now, show some dummy data
        val dummyEvents = listOf(
            Event(
                id = "1",
                title = "Calculus Study Session",
                description = "Review for midterm exam",
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 7200000, // 2 hours later
                location = "Library Room 302",
                type = Event.EventType.STUDY_SESSION
            ),
            Event(
                id = "2",
                title = "Programming Assignment Due",
                description = "Submit final project",
                startTime = System.currentTimeMillis() + 86400000, // 1 day later
                endTime = System.currentTimeMillis() + 86400000,
                location = "Online",
                type = Event.EventType.ASSIGNMENT
            )
        )
        adapter.updateEvents(dummyEvents)
    }

    private fun setupFab() {
        fab.setOnClickListener {
            // TODO: Show add event dialog
            Toast.makeText(context, "Add event functionality coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
} 