package com.dreamsoftware.lockbuddy.signin

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)