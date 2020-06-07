package com.robien.whattocook.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.robien.whattocook.R
import com.robien.whattocook.models.User


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Hide the app bar
        if (supportActionBar != null)
            supportActionBar?.hide()

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Get references to widgets
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val pwEditText = findViewById<EditText>(R.id.pwEditText)
        var confirmPwEditText = findViewById<EditText>(R.id.confirmPwText)
        val termsAndCondCheckBox = findViewById<CheckBox>(R.id.tac_checkBox)
        val registerButton = findViewById<Button>(R.id.signup_button)
        val progressBar = findViewById<ProgressBar>(R.id.regProgressBar)

        // Change text color of checkbox text and listen to changes
        // If checked, enable button, if not, disable it
        termsAndCondCheckBox.setTextColor(Color.WHITE)
        termsAndCondCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                enableButton(registerButton)
            else
                disableButton(registerButton)
        }

        // Create user object to pass to sign up function
        val email = emailEditText?.text
        val password = pwEditText?.text
        val newUser = User(email, password)

        // Call Firebase sign up method on button press
        registerButton.setOnClickListener() {
            // Turn on progress bar before and during operation

            // Sign up using Firebase only fi form is properly filled out
            if(validateForm(emailEditText, pwEditText, confirmPwEditText))
                signUpUser(newUser)

            // Turn off progress bar after operation

        }
    }

    // Validate form before sign up process
    // Return true if form is filled out properly, false if not
    private fun validateForm(emailET: EditText, pwET: EditText, confirmedPwET: EditText): Boolean {
        when {
            emailET.text.isEmpty() -> {
                emailET.error = "Email field cannot be left blank."
                emailET.isFocusable = true
                return false
            }
            pwET.text.isEmpty() -> {
                pwET.error = "Password field cannot be left blank."
                pwET.isFocusable = true
                return false
            }
            confirmedPwET.text.isEmpty() -> {
                confirmedPwET.error = "Confirm password field cannot be left blank."
                confirmedPwET.isFocusable = true
                return false
            }
            pwET.text.length < 6 -> {
                pwET.error = "Password must be at least 6 characters."
                pwET.isFocusable = true
                return false
            }
            pwET.text.toString() != confirmedPwET.text.toString() -> {
                confirmedPwET.error = "Passwords did not match."
                pwET.isFocusable = true
                return false
            }
            else -> {
                return true
            }
        }
    }

    // Sign up method using Firebase auth
    private fun signUpUser(user: User) {
        // Sign up user using Firebase
        auth.createUserWithEmailAndPassword(user.email.toString(), user.password.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Sign-up success", "createUserWithEmail:success")
                    showSuccessfulRegToast()
                    goToHomePage()
                } else {
                    showUnsuccessfulRegToast()
                }
            }
    }

    // Show toast if sign up was successful
    private fun showSuccessfulRegToast() {
        runOnUiThread {
            run() {
                //val successfulToast = Toast.makeText(applicationContext,"Sign up successful!",Toast.LENGTH_LONG)
                val successfulToast = Toast.makeText(applicationContext,"Sign up successful!",Toast.LENGTH_LONG)
                successfulToast.setGravity(Gravity.BOTTOM, 0,0)
                successfulToast.show()
            }
        }
    }

    // SHow toast if sign up was unsuccessful
    private fun showUnsuccessfulRegToast() {
        runOnUiThread {
            run() {
                //val successfulToast = Toast.makeText(applicationContext,"Sign up successful!",Toast.LENGTH_LONG)
                val successfulToast = Toast.makeText(applicationContext,"Sign up failed. " +
                        "Email has already been signed up. Please choose another email.",Toast.LENGTH_LONG)
                successfulToast.setGravity(Gravity.BOTTOM, 0,0)
                successfulToast.show()
            }
        }
    }

    // Disable button method to invoke if user has not agreed to terms and conditions
    private fun disableButton(button: Button) {
        button.isEnabled = false
        button.isClickable = false
        button.setTextColor(Color.WHITE)
        button.setBackgroundColor(Color.GRAY)
    }

    // Enable button method to invoke if user has agreed to terms and conditions
    private fun enableButton(button: Button) {
        button.isEnabled = true
        button.isClickable = true
        button.setTextColor(Color.BLACK)
        button.setBackgroundColor(Color.WHITE)
    }

    // Intent to go to home page activity
    private fun goToHomePage() {
        // Go to Register Page view
        val homeIntent = Intent(this, HomepageActivity::class.java)
        // start your next activity
        startActivity(homeIntent)
    }

}
