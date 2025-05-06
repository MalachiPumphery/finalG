package com.example.mobileappdev2025.chat

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileappdev2025.R
import com.example.mobileappdev2025.adapters.ChatAdapter
import com.example.mobileappdev2025.models.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var studyGroupId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        studyGroupId = intent.getStringExtra("studyGroupId") ?: run {
            Toast.makeText(this, "Error: Study group ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        supportActionBar?.title = intent.getStringExtra("studyGroupName") ?: "Chat"

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.chat_recycler_view)
        messageInput = findViewById(R.id.message_input)
        sendButton = findViewById(R.id.send_button)
        progressBar = findViewById(R.id.progress_bar)

        setupRecyclerView()
        setupClickListeners()
        listenForMessages()
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(mutableListOf())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }
    }

    private fun setupClickListeners() {
        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
                messageInput.text.clear()
            }
        }
    }

    private fun sendMessage(messageText: String) {
        val currentUser = auth.currentUser ?: run {
            Toast.makeText(this, "Error: User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        sendButton.isEnabled = false
        progressBar.visibility = View.VISIBLE

        val message = ChatMessage(
            studyGroupId = studyGroupId,
            senderId = currentUser.uid,
            senderName = currentUser.displayName ?: "Anonymous",
            message = messageText
        )

        db.collection("messages")
            .add(message)
            .addOnSuccessListener {
                sendButton.isEnabled = true
                progressBar.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                sendButton.isEnabled = true
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Error sending message: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun listenForMessages() {
        progressBar.visibility = View.VISIBLE
        
        db.collection("messages")
            .whereEqualTo("studyGroupId", studyGroupId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                progressBar.visibility = View.GONE
                
                if (e != null) {
                    Toast.makeText(this, "Error loading messages: ${e.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                val messages = mutableListOf<ChatMessage>()
                snapshot?.documents?.forEach { doc ->
                    doc.toObject(ChatMessage::class.java)?.let { messages.add(it) }
                }
                chatAdapter.updateMessages(messages)
                if (messages.isNotEmpty()) {
                    recyclerView.smoothScrollToPosition(messages.size - 1)
                }
            }
    }
} 