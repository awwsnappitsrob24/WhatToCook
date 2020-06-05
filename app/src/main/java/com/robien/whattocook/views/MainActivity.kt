package com.robien.whattocook.views

import android.content.Intent
import com.robien.whattocook.R
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.robien.whattocook.models.User
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

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

        // Initialize Firebase auth
        auth = Firebase.auth

        // Set edit text background colors
        emailEditText.setBackgroundColor(Color.parseColor("#E1E1E7"))
        pwEditText.setBackgroundColor(Color.parseColor("#E1E1E7"))

        // Create user object using user entered fields
        var email = emailEditText?.text
        var password = pwEditText?.text
        var user = User(email, password)

        // Set button listeners
        // Login button authenticates user using Firebase only if the form is valid
        signInButton.setOnClickListener {
            if(validateForm(emailEditText, pwEditText))
                loginUser(user.email.toString(), user.password.toString())
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

    // Validates the edit texts
    // Return true if form is properly filled out, false if not.
    private fun validateForm(emailET: EditText, pwET: EditText): Boolean {
        return when {
            emailET.text.isEmpty() -> {
                emailET.error = "Email cannot be empty."
                emailET.isFocusable = true
                false
            }
            pwET.text.isEmpty() -> {
                pwET.error = "Password cannot be empty."
                pwET.isFocusable = true
                false
            }
            else -> {
                true
            }
        }
    }

    // Sign in the user using Firebase auth
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    goToHomePage()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("Login", "signInWithEmail:failure", task.exception)
                    showUnsuccessfulLoginToast()
                }
            }
    }

    // Use intent to go to homepage activity
    private fun goToHomePage() {
        // Go to Register Page view
        val homeIntent = Intent(this, HomepageActivity::class.java)
        // start your next activity
        startActivity(homeIntent)
    }

    // Use intent to go to sign up page activity
    private fun goToSignUpPage() {
        // Go to Register Page view
        val regIntent = Intent(this, RegisterActivity::class.java)
        // start your next activity
        startActivity(regIntent)
    }

    // SHow this toast if login was unsuccessful
    private fun showUnsuccessfulLoginToast() {
        runOnUiThread {
            run() {
                //val successfulToast = Toast.makeText(applicationContext,"Sign up successful!",Toast.LENGTH_LONG)
                val successfulToast = Toast.makeText(applicationContext,"Login failed. " +
                        "Your email and/or password was incorrect.",Toast.LENGTH_LONG)
                successfulToast.setGravity(Gravity.BOTTOM, 0,0)
                successfulToast.show()
            }
        }
    }

}
