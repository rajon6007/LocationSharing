package com.example.locationsharingapp_dipti_16

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.locationsharingapp_dipti_16.ViewModel16.AuthenticationViewModel16
import com.example.locationsharingapp_dipti_16.ViewModel16.FireStoreViewModel16
import com.example.locationsharingapp_dipti_16.databinding.ActivityRegister16Binding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity16 : AppCompatActivity() {
    private lateinit var binding: ActivityRegister16Binding
    private lateinit var authenticationViewModel: AuthenticationViewModel16
    private lateinit var firestoreViewModel: FireStoreViewModel16
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegister16Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel16::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FireStoreViewModel16::class.java)

        binding.registerBtn.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passEt.text.toString()
            val confirmPassword = binding.rePassEt.text.toString()
            val location = "Don't found any location yet"
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                    .show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            } else {
                authenticationViewModel.register(email, password, {
                    firestoreViewModel.saveUser(this, authenticationViewModel.getCurrentUserId(), name, email, location)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
            }
        }
        binding.loginTxt.setOnClickListener {
            startActivity(Intent(this, LoginActivity16::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this@RegisterActivity16, MainActivity::class.java))
            finish()
        }
    }
}