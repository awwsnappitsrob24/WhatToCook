package com.robien.whattocook.views

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify
import com.robien.whattocook.R
import com.robien.whattocook.models.User

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize and add Amplify Auth plugin
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        try {
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }


        // Get references to widgets
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val pwEditText = findViewById<EditText>(R.id.pwEditText)
        var confirmPwEditText = findViewById<EditText>(R.id.confirmPwText)
        val termsAndCondCheckBox = findViewById<CheckBox>(R.id.tac_checkBox)
        val registerButton = findViewById<Button>(R.id.signup_button)
        val progressBar = findViewById<ProgressBar>(R.id.progress_circular)

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
        val email = emailEditText.text
        val password = pwEditText.text
        val newUser = User(email, password)

        // Call Amplify sign up method on button press
        registerButton.setOnClickListener() {
            // Show progress bar while signing up user
            progressBar.visibility = View.VISIBLE

            // Sign up using Amplify
            signUpUser(newUser)

            // Turn off progress bar after operation
            progressBar.visibility = View.GONE
        }
    }

    private fun signUpUser(user: User) {
        Log.d("user_object_email", user.email.toString())
        Log.d("user_object_pw", user.password.toString())
        // If successful, show toast with successful message then go to home page
        // If unsuccessful, show toast with error message.
        // This is all performed in the background thread
        Amplify.Auth.signUp(
            user.email.toString(),
            user.password.toString(),
            AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), user.email.toString()).build(),
            { result -> showSuccessfulRegToast(result.toString())
                goToHomePage()},
            { error -> showUnsuccessfulRegToast(error.toString()) }
        )
    }

    private fun showSuccessfulRegToast(result: String) {
        runOnUiThread {
            run() {
                //val successfulToast = Toast.makeText(applicationContext,"Sign up successful!",Toast.LENGTH_LONG)
                val successfulToast = Toast.makeText(applicationContext,result,Toast.LENGTH_LONG)
                successfulToast.setGravity(Gravity.BOTTOM, 0,0)
                successfulToast.show()

            }
        }
    }

    private fun showUnsuccessfulRegToast(error: String) {
        runOnUiThread {
            run() {
                //val unsuccessfulToast = Toast.makeText(applicationContext,"Sign up failed." +
                   // "Make sure that your password is greater than or equal to 6 characters",Toast.LENGTH_LONG)
                val unsuccessfulToast = Toast.makeText(applicationContext,error,Toast.LENGTH_LONG)
                unsuccessfulToast.setGravity(Gravity.BOTTOM, 0,0)
                unsuccessfulToast.show()
                Log.d("error_message", error)
            }
        }
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
