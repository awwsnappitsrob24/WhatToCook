package com.robien.whattocook.models

import android.text.Editable

// val for email (only one email per user)
// var for password since it will be mutable (can be updated if forgotten)
data class User(val email: Editable, var password: Editable)