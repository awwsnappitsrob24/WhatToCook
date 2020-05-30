package com.robien.whattocook.views

import android.content.Intent
import com.robien.whattocook.R
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide the app bar
        if (supportActionBar != null)
            supportActionBar?.hide()

        // Get reference to the widgets in the login screen
        val emailEditText = findViewById<EditText>(R.id.email_editText)
        val pwEditText = findViewById<EditText>(R.id.password_edittext)
        val signInButton = findViewById<Button>(R.id.login_button)
        val signUpButton = findViewById<Button>(R.id.register_button)
        val updatePwTextView = findViewById<TextView>(R.id.update_pw_textview)

        // Set edit text background colors
        emailEditText.setBackgroundColor(Color.parseColor("#E1E1E7"))
        pwEditText.setBackgroundColor(Color.parseColor("#E1E1E7"))

        // Set button listeners
        // Login button authenticates user
        signInButton.setOnClickListener {

        }

        // Register button signs up the user
        signUpButton.setOnClickListener {
            goToSignUpPage()
        }

        // Set the update password text view to be clickable and set color to blue
        updatePwTextView.setTextColor(Color.parseColor("#00E3E7"))
        updatePwTextView.setOnClickListener {
            // Call the update password function here

        }
    }

    private fun goToSignUpPage() {
        // Go to Register Page view
        val regIntent = Intent(this, RegisterActivity::class.java)
        // start your next activity
        startActivity(regIntent)
    }
}
