package com.example.locationsharingapp_dipti_16

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationsharingapp_dipti_16.Adapter16.UserAdapter16
import com.example.locationsharingapp_dipti_16.ViewModel16.AuthenticationViewModel16
import com.example.locationsharingapp_dipti_16.ViewModel16.FireStoreViewModel16
import com.example.locationsharingapp_dipti_16.ViewModel16.LocationViewModel16
import com.example.locationsharingapp_dipti_16.databinding.FragmentFriends16Binding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class FriendsFragment16 : Fragment() {
    private lateinit var binding: FragmentFriends16Binding
    private lateinit var firestoreViewModel: FireStoreViewModel16
    private lateinit var authenticationViewModel: AuthenticationViewModel16
    private lateinit var userAdapter: UserAdapter16
    private lateinit var locationViewModel: LocationViewModel16
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Location Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriends16Binding.inflate(inflater,container, false)

        firestoreViewModel = ViewModelProvider(this).get(FireStoreViewModel16::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel16::class.java)
        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel16::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationViewModel.initializeFusedLocationClient(fusedLocationClient)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getLocation()
        }
        userAdapter = UserAdapter16(emptyList())
        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        fetchUsers()

        binding.locationBtn.setOnClickListener {
            startActivity(Intent(requireContext(),MapsActivity16::class.java))
        }


        return binding.root
    }

    private fun fetchUsers() {
        firestoreViewModel.getAllUsers(requireContext()){
            userAdapter.updateData(it)
        }
    }

    private fun getLocation() {
        locationViewModel.getLastLocation {
            authenticationViewModel.getCurrentUserId().let { userId ->
                firestoreViewModel.updateUserLocation(requireContext(),userId, it)
            }
        }
    }

}