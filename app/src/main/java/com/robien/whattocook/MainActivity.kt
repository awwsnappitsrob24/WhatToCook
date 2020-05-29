package com.robien.whattocook

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide the app bar
        if (supportActionBar != null)
            supportActionBar?.hide()

        // Get reference to the widgets in the login screen
        var emailEditText = findViewById<EditText>(R.id.email_editText)
        var pwEditText = findViewById<EditText>(R.id.password_edittext)
        var loginButton = findViewById<Button>(R.id.login_button)
        var registerButton = findViewById<Button>(R.id.register_button)
        var updatePwTextView = findViewById<TextView>(R.id.update_pw_textview)

        // Set edit text background colors
        //emailEditText.setBackgroundColor(Color.parseColor("#E1E1E7"))
        //pwEditText.setBackgroundColor(Color.parseColor("#E1E1E7"))

        // Set the update password text view to be clickable and set color to blue
        updatePwTextView.setTextColor(Color.parseColor("#00E3E7"))
        updatePwTextView.setOnClickListener {
            // Call the update password function here

        }


    }
}
