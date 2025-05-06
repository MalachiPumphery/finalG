package com.example.mobileappdev2025.models

data class Course(
    val id: String = "",
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val enrolledStudents: List<String> = listOf(),
    val studyGroups: List<String> = listOf()
) 