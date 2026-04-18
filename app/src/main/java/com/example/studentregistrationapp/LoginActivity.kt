package com.example.studentregistrationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etLoginEmail = findViewById<EditText>(R.id.etLoginEmail)
        val etLoginPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvForgot = findViewById<TextView>(R.id.tvForgot)
        val llRequestAccess = findViewById<LinearLayout>(R.id.llRequestAccess)

        setupPasswordVisibilityToggle(etLoginPassword)

        tvForgot.setOnClickListener {
            startActivity(Intent(this, UpdatePasswordActivity::class.java))
        }

        llRequestAccess.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email = etLoginEmail.text.toString().trim()
            val pass = etLoginPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Retrieve stored data from SharedPreferences
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val storedEmail = sharedPreferences.getString("EMAIL", null)
            val storedPassword = sharedPreferences.getString("PASSWORD", null)

            if (storedEmail == null) {
                Toast.makeText(this, "No user registered. Please request access.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email == storedEmail && pass == storedPassword) {
                // Login successful - retrieve all details
                val name = sharedPreferences.getString("NAME", "")
                val course = sharedPreferences.getString("COURSE", "")
                val year = sharedPreferences.getString("YEAR", "")
                val gender = sharedPreferences.getString("GENDER", "")

                val intent = Intent(this, SummaryActivity::class.java).apply {
                    putExtra("NAME", name)
                    putExtra("COURSE", course)
                    putExtra("YEAR", year)
                    putExtra("GENDER", gender)
                    putExtra("EMAIL", email)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @android.annotation.SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordVisibilityToggle(editText: EditText) {
        var isVisible = false
        editText.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                // Check if touch is on the drawableEnd (right side)
                val drawableRight = editText.compoundDrawables[2]
                if (drawableRight != null && event.rawX >= (editText.right - editText.paddingRight - drawableRight.bounds.width())) {
                    isVisible = !isVisible
                    if (isVisible) {
                        editText.transformationMethod = android.text.method.HideReturnsTransformationMethod.getInstance()
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0)
                    } else {
                        editText.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0)
                    }
                    editText.setSelection(editText.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }
}
