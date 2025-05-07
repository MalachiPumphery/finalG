package com.example.mobileappdev2025.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileappdev2025.R
import com.example.mobileappdev2025.models.StudyGroup

class StudyGroupsAdapter(
    private var groups: List<StudyGroup> = listOf(),
    private val onGroupClick: (StudyGroup) -> Unit
) : RecyclerView.Adapter<StudyGroupsAdapter.GroupViewHolder>() {

    class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.group_name)
        val descriptionText: TextView = view.findViewById(R.id.group_description)
        val membersCountText: TextView = view.findViewById(R.id.members_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_study_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]
        holder.nameText.text = group.name
        holder.descriptionText.text = group.description
        holder.membersCountText.text = "${group.members.size} members"
        
        holder.itemView.setOnClickListener {
            onGroupClick(group)
        }
    }

    override fun getItemCount() = groups.size

    fun updateGroups(newGroups: List<StudyGroup>) {
        groups = newGroups
        notifyDataSetChanged()
    }
} 