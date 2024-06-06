package com.dreamsoftware.vaultkeeper.domain.model

data class SignUpBO(
    val email: String,
    val password: String,
    val confirmPassword: String
) {
    companion object {
        const val FIELD_EMAIL = "email"
        const val FIELD_PASSWORD = "password"
        const val FIELD_CONFIRM_PASSWORD = "confirmPassword"
    }
}
