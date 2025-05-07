package com.example.mobileappdev2025.models

data class StudyGroup(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val courseId: String = "",
    val members: List<String> = listOf(),
    val createdAt: Long = System.currentTimeMillis()
)

data class StudySession(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val dateTime: Long = 0,
    val location: String = "",
    val attendees: List<String> = listOf()
) 