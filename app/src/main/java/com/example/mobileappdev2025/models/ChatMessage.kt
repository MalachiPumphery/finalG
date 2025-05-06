package com.example.mobileappdev2025.models

data class ChatMessage(
    val id: String = "",
    val studyGroupId: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
) 