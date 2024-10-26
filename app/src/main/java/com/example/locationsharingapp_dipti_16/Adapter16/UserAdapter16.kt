package com.example.locationsharingapp_dipti_16.Adapter16

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locationsharingapp_dipti_16.Model16.User16
import com.example.locationsharingapp_dipti_16.databinding.ItemUser16Binding

class UserAdapter16 (private var userList: List<User16>): RecyclerView.Adapter<UserAdapter16.UserViewHolder>() {
    class UserViewHolder(private val binding: ItemUser16Binding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User16){
            binding.apply {
                nameTxt.text = user.displayname
                emailTxt.text = user.email
                locationTxt.text = user.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUser16Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.bind(user)

    }
    fun updateData(newList: List<User16>) {
        userList = newList
        notifyDataSetChanged()
    }
}