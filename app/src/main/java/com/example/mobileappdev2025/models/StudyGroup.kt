package com.example.mobileappdev2025.models

data class StudyGroup(
    val id: String = "",
    val name: String = "",
    val courseId: String = "",
    val description: String = "",
    val members: List<String> = listOf(),
    val studySessions: List<StudySession> = listOf(),
    val maxMembers: Int = 6
)

data class StudySession(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val dateTime: Long = 0,
    val location: String = "",
    val attendees: List<String> = listOf()
) 