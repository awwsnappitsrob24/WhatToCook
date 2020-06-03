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
            //showProgressBar(progressBar)

            // Sign up using Firebase
            signUpUser(newUser)

            // Turn off progress bar after operation
            //hideProgressBar(progressBar)
        }
    }

    private fun signUpUser(user: User) {
        Log.d("user_object_email", user.email.toString())
        Log.d("user_object_pw", user.password.toString())

        // If successful, show toast with successful message then go to home page
        // If unsuccessful, show toast with error message
        // Sign up user using Firebase
        auth.createUserWithEmailAndPassword(user.email.toString(), user.password.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Sign-up success", "createUserWithEmail:success")
                    showSuccessfulRegToast()
                    goToHomePage()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("Sign-up failed", "createUserWithEmail:failure", task.exception)
                    showUnsuccessfulRegToast()
                }
            }
    }

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

    private fun showUnsuccessfulRegToast() {
        runOnUiThread {
            run() {
                val unsuccessfulToast = Toast.makeText(applicationContext,"Sign up failed." +
                        "Make sure that your email is formatted correctly and/or your password " +
                        "has at least 6 characters.",Toast.LENGTH_LONG)
                unsuccessfulToast.setGravity(Gravity.BOTTOM, 0,0)
                unsuccessfulToast.show()
            }
        }
    }

    private fun showProgressBar(bar: ProgressBar) {
        //runOnUiThread {
        //    run() {
                bar.visibility = View.VISIBLE
        //    }
        //}
    }

    private fun hideProgressBar(bar: ProgressBar) {
        //runOnUiThread {
        //    run() {
                bar.visibility = View.INVISIBLE
        //    }
        //}
    }



    private fun disableButton(button: Button) {
        button.isEnabled = false
        button.isClickable = false
        button.setTextColor(Color.WHITE)
        button.setBackgroundColor(Color.GRAY)
    }

    private fun enableButton(button: Button) {
        button.isEnabled = true
        button.isClickable = true
        button.setTextColor(Color.BLACK)
        button.setBackgroundColor(Color.WHITE)
    }

    private fun goToHomePage() {
        // Go to Register Page view
        val homeIntent = Intent(this, HomepageActivity::class.java)
        // start your next activity
        startActivity(homeIntent)
    }

}
