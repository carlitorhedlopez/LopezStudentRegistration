package com.example.studentregistrationapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val spCourse = findViewById<Spinner>(R.id.spCourse)
        val spYear = findViewById<Spinner>(R.id.spYear)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // Setup responsive eye for password field
        setupPasswordVisibilityToggle(etPassword)

        val courses = arrayOf("Select a program", "B.S. Computer Science", "B.S. Information Technology", "B.S. Information Systems", "Associate in Computer Technology")
        val years = arrayOf("Select year", "Freshman (1st Year)", "Sophomore (2nd Year)", "Junior (3rd Year)", "Senior (4th Year)")

        val courseAdapter = ArrayAdapter(this, R.layout.spinner_item, courses)
        courseAdapter.setDropDownViewResource(R.layout.spinner_item)
        spCourse.adapter = courseAdapter

        val yearAdapter = ArrayAdapter(this, R.layout.spinner_item, years)
        yearAdapter.setDropDownViewResource(R.layout.spinner_item)
        spYear.adapter = yearAdapter

        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val course = spCourse.selectedItem.toString()
            val year = spYear.selectedItem.toString()

            val selectedGenderId = rgGender.checkedRadioButtonId

            if (name.isEmpty()) {
                etName.error = "Name is required"
                etName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Enter a valid email"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Password is required"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            if (selectedGenderId == -1) {
                Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (spCourse.selectedItemPosition == 0) {
                Toast.makeText(this, "Please select a course", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (spYear.selectedItemPosition == 0) {
                Toast.makeText(this, "Please select a year level", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val genderRadio = findViewById<RadioButton>(selectedGenderId)
            val gender = genderRadio.text.toString()

            val intent = Intent(this, ConfirmationActivity::class.java)
            intent.putExtra("NAME", name)
            intent.putExtra("COURSE", course)
            intent.putExtra("YEAR", year)
            intent.putExtra("GENDER", gender)
            intent.putExtra("EMAIL", email)
            intent.putExtra("PASSWORD", password)
            startActivity(intent)
        }
    }

    @android.annotation.SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordVisibilityToggle(editText: EditText) {
        var isVisible = false
        editText.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                val drawableRight = editText.compoundDrawables[2]
                if (drawableRight != null && event.rawX >= (editText.right - editText.paddingRight - drawableRight.bounds.width())) {
                    isVisible = !isVisible
                    if (isVisible) {
                        editText.transformationMethod = android.text.method.HideReturnsTransformationMethod.getInstance()
                        editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_visibility, 0)
                    } else {
                        editText.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
                        editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_visibility_off, 0)
                    }
                    editText.setSelection(editText.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }
}
