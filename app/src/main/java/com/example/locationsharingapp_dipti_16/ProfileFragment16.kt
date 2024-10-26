package com.example.locationsharingapp_dipti_16

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.locationsharingapp_dipti_16.ViewModel16.AuthenticationViewModel16
import com.example.locationsharingapp_dipti_16.ViewModel16.FireStoreViewModel16
import com.example.locationsharingapp_dipti_16.ViewModel16.LocationViewModel16
import com.example.locationsharingapp_dipti_16.databinding.FragmentProfile16Binding
import com.google.firebase.auth.FirebaseAuth



class ProfileFragment16 : Fragment() {

    private lateinit var binding: FragmentProfile16Binding
    private lateinit var authViewModel: AuthenticationViewModel16
    private lateinit var firestoreViewModel: FireStoreViewModel16
    private lateinit var locationViewModel: LocationViewModel16
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfile16Binding.inflate(inflater, container, false)

        authViewModel = ViewModelProvider(this).get(AuthenticationViewModel16::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FireStoreViewModel16::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel16::class.java)


        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity16::class.java))

        }

        binding.homeBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        loadUserInfo()
        binding.updateBtn.setOnClickListener {
            val newName = binding.nameEt.text.toString()
            val newLocation = binding.locationEt.text.toString()

            updateBtn(newName, newLocation)
        }

        return binding.root
    }

    private fun updateBtn(newName: String, newLocation: String) {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.uid
            firestoreViewModel.updateUser(requireContext(), userId, newName, newLocation)
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserInfo() {
        val currentUser = authViewModel.getCurrentUser()
        if(currentUser != null) {
            binding.emailEt.setText(currentUser.email)
            firestoreViewModel.getUser(requireContext(), currentUser.uid){ user ->
                if (currentUser.displayName != null) {
                    binding.nameEt.setText(currentUser.displayName)
                }
            }
        }else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }

    }
}