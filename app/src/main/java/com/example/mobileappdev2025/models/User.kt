package com.example.mobileappdev2025.models

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val major: String = "",
    val enrolledCourses: List<String> = listOf(),
    val studyGroups: List<String> = listOf()
) 