package com.example.mobileappdev2025.models

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val startTime: Long = 0,
    val endTime: Long = 0,
    val location: String = "",
    val type: EventType = EventType.STUDY_SESSION,
    val participants: List<String> = listOf(),
    val createdAt: Long = System.currentTimeMillis()
)

enum class EventType {
    STUDY_SESSION,
    EXAM,
    ASSIGNMENT,
    OTHER
} 