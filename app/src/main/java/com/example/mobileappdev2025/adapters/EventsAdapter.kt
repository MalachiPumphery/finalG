package com.example.mobileappdev2025.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileappdev2025.R
import com.example.mobileappdev2025.models.Event
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter(
    private var events: List<Event> = listOf(),
    private val onEventClick: (Event) -> Unit
) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.event_title)
        val timeText: TextView = view.findViewById(R.id.event_time)
        val locationText: TextView = view.findViewById(R.id.event_location)
        val typeText: TextView = view.findViewById(R.id.event_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleText.text = event.title
        holder.timeText.text = "${timeFormat.format(event.startTime)} - ${timeFormat.format(event.endTime)}"
        holder.locationText.text = event.location
        holder.typeText.text = event.type.name.replace("_", " ").lowercase()
            .replaceFirstChar { it.uppercase() }
        
        holder.itemView.setOnClickListener {
            onEventClick(event)
        }
    }

    override fun getItemCount() = events.size

    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
} 