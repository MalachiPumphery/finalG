package com.example.mobileappdev2025.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobileappdev2025.LoginActivity
import com.example.mobileappdev2025.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var profileImage: ShapeableImageView
    private lateinit var nameText: MaterialTextView
    private lateinit var emailText: MaterialTextView
    private lateinit var editProfileButton: MaterialButton
    private lateinit var notificationsButton: MaterialButton
    private lateinit var privacyButton: MaterialButton
    private lateinit var logoutButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupUserInfo()
        setupClickListeners()
    }

    private fun initializeViews(view: View) {
        profileImage = view.findViewById(R.id.profile_image)
        nameText = view.findViewById(R.id.name_text)
        emailText = view.findViewById(R.id.email_text)
        editProfileButton = view.findViewById(R.id.edit_profile_button)
        notificationsButton = view.findViewById(R.id.notifications_button)
        privacyButton = view.findViewById(R.id.privacy_button)
        logoutButton = view.findViewById(R.id.logout_button)
    }

    private fun setupUserInfo() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            nameText.text = it.displayName ?: "User"
            emailText.text = it.email
            // TODO: Load profile image if available
        }
    }

    private fun setupClickListeners() {
        editProfileButton.setOnClickListener {
            // TODO: Implement edit profile functionality
            Toast.makeText(context, "Edit profile functionality coming soon", Toast.LENGTH_SHORT).show()
        }

        notificationsButton.setOnClickListener {
            // TODO: Implement notifications settings
            Toast.makeText(context, "Notifications settings coming soon", Toast.LENGTH_SHORT).show()
        }

        privacyButton.setOnClickListener {
            // TODO: Implement privacy settings
            Toast.makeText(context, "Privacy settings coming soon", Toast.LENGTH_SHORT).show()
        }

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }
} 