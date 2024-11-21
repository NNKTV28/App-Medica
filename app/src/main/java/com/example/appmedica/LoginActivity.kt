package com.example.appmedica

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmedica.data.DatabaseHelper
import com.example.appmedica.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = DatabaseHelper(this)

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            val user = dbHelper.getUser(email, password)
            if (user != null) {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USER_NAME", user.name)
                    putExtra("USER_EMAIL", user.email)
                }
                startActivity(intent)
                finish()
            } else {
                android.widget.Toast.makeText(this, "Invalid credentials", android.widget.Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}