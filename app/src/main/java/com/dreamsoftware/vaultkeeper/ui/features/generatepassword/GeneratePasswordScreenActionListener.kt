package com.dreamsoftware.vaultkeeper.ui.features.generatepassword

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface GeneratePasswordScreenActionListener: IBrownieScreenActionListener {
    fun onPasswordLength(newLength: Int)
    fun onLowerCaseChanged(hasLowerCase: Boolean)
    fun onUpperCaseChanged(hasUpperCase: Boolean)
    fun onDigitsChanged(hasDigits: Boolean)
    fun onSpecialCharactersChanged(hasSpecialCharacters: Boolean)
    fun onValidateAndSave()
    fun onPasswordCopied()
}