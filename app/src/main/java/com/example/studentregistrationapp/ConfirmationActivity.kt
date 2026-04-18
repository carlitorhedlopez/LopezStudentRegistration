package com.example.studentregistrationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val tvNameVal = findViewById<TextView>(R.id.tvNameVal)
        val tvCourseVal = findViewById<TextView>(R.id.tvCourseVal)
        val tvYearVal = findViewById<TextView>(R.id.tvYearVal)
        val tvGenderVal = findViewById<TextView>(R.id.tvGenderVal)
        val tvEmailVal = findViewById<TextView>(R.id.tvEmailVal)

        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        val name = intent.getStringExtra("NAME") ?: ""
        val course = intent.getStringExtra("COURSE") ?: ""
        val year = intent.getStringExtra("YEAR") ?: ""
        val gender = intent.getStringExtra("GENDER") ?: ""
        val email = intent.getStringExtra("EMAIL") ?: ""
        val password = intent.getStringExtra("PASSWORD") ?: ""

        tvNameVal.text = name
        tvCourseVal.text = course
        tvYearVal.text = year
        tvGenderVal.text = gender
        tvEmailVal.text = email

        btnEdit.setOnClickListener {
            // Returns to MainActivity to edit details
            finish()
        }

        btnConfirm.setOnClickListener {
            // Store data inside the app using SharedPreferences
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            
            editor.putString("NAME", name)
            editor.putString("COURSE", course)
            editor.putString("YEAR", year)
            editor.putString("GENDER", gender)
            editor.putString("EMAIL", email)
            editor.putString("PASSWORD", password)
            editor.apply()

            // After confirmation, return strictly back to login as requested.
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
