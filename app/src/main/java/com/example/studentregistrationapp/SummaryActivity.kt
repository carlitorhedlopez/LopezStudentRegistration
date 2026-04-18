package com.example.studentregistrationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val tvNameVal = findViewById<TextView>(R.id.tvNameVal)
        val tvCourseVal = findViewById<TextView>(R.id.tvCourseVal)
        val tvYearVal = findViewById<TextView>(R.id.tvYearVal)
        val tvGenderVal = findViewById<TextView>(R.id.tvGenderVal)
        val tvEmailVal = findViewById<TextView>(R.id.tvEmailVal)

        val btnReturnToLogin = findViewById<Button>(R.id.btnReturnToLogin)
        val tvWelcomeHeader = findViewById<TextView>(R.id.tvWelcomeHeader)

        val name = intent.getStringExtra("NAME") ?: ""
        val course = intent.getStringExtra("COURSE") ?: ""
        val year = intent.getStringExtra("YEAR") ?: ""
        val gender = intent.getStringExtra("GENDER") ?: ""
        val email = intent.getStringExtra("EMAIL") ?: ""

        val firstName = name.substringBefore(" ")
        tvWelcomeHeader.text = "Welcome back,\n$firstName"

        tvNameVal.text = name
        tvCourseVal.text = course
        tvYearVal.text = year
        tvGenderVal.text = gender
        tvEmailVal.text = email

        btnReturnToLogin.setOnClickListener {
            // Clears all activities on top and returns us cleanly back to the front Login screen.
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finishAffinity()
        }
    }
}
