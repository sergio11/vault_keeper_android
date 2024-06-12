package com.dreamsoftware.vaultkeeper.ui.features.savepassword


interface SavePasswordScreenActionListener {
    fun onResetSuggestions()
    fun onAccountNameUpdated(newName: String)
    fun onFilterByAccountName(name: String)
    fun onUsernameUpdated(username: String)
    fun onEmailUpdated(email: String)
    fun onMobileNumberUpdated(mobileNumber: String)
    fun onPasswordUpdated(password: String)
    fun onNoteUpdated(note: String)
    fun onGenerateRandomPassword()
    fun onSave()
    fun onCancel()
}