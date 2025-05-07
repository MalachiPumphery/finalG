package com.example.mobileappdev2025.models

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val startTime: Long,
    val endTime: Long,
    val location: String,
    val type: EventType // Use the enum here
) {
    enum class EventType {
        STUDY_SESSION,
        ASSIGNMENT,
        MEETING,
        OTHER // You can add more types here
    }
}