package com.dreamsoftware.vaultkeeper.ui.features.generatepassword

interface GeneratePasswordScreenActionListener {
    fun onPasswordLength(newLength: Int)
    fun onLowerCaseChanged(hasLowerCase: Boolean)
    fun onUpperCaseChanged(hasUpperCase: Boolean)
    fun onDigitsChanged(hasDigits: Boolean)
    fun onSpecialCharactersChanged(hasSpecialCharacters: Boolean)
    fun onValidateAndSave()
    fun onPasswordCopied()
}