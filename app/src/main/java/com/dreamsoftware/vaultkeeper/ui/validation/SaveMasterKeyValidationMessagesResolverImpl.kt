package com.dreamsoftware.vaultkeeper.ui.validation

import android.content.Context
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.validation.ISaveMasterKeyValidationMessagesResolver

internal class SaveMasterKeyValidationMessagesResolverImpl(
    private val context: Context
): ISaveMasterKeyValidationMessagesResolver {
    override fun getKeyEmptyError(): String =
        context.getString(R.string.master_key_empty_error)

    override fun getKeyMismatchError(): String =
        context.getString(R.string.master_key_mismatch_error)

    override fun getKeyIncorrectLengthError(): String =
        context.getString(R.string.masterkey_incorrect_length_error)
}