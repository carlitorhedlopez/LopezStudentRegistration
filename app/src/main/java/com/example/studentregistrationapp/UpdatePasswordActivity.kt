package com.example.studentregistrationapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UpdatePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        val etEmail = findViewById<EditText>(R.id.etUpdateEmail)
        val etOldPassword = findViewById<EditText>(R.id.etOldPassword)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        
        val btnUpdate = findViewById<Button>(R.id.btnUpdatePassword)
        val tvBack = findViewById<TextView>(R.id.tvBackToLogin)

        // Set up responsive eyes for all password fields
        setupPasswordVisibilityToggle(etOldPassword)
        setupPasswordVisibilityToggle(etNewPassword)
        setupPasswordVisibilityToggle(etConfirmPassword)

        btnUpdate.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val oldPass = etOldPassword.text.toString().trim()
            val newPass = etNewPassword.text.toString().trim()
            val confirmPass = etConfirmPassword.text.toString().trim()

            if (email.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPass != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Retrieve stored data from SharedPreferences
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val storedEmail = sharedPreferences.getString("EMAIL", null)
            val storedPassword = sharedPreferences.getString("PASSWORD", null)

            if (storedEmail == null) {
                Toast.makeText(this, "No user registered. Please register first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate Email and Old Password
            if (email == storedEmail && oldPass == storedPassword) {
                // Success - Update the password
                val editor = sharedPreferences.edit()
                editor.putString("PASSWORD", newPass)
                editor.apply()

                Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Invalid email or old password", Toast.LENGTH_SHORT).show()
            }
        }

        tvBack.setOnClickListener {
            finish()
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
