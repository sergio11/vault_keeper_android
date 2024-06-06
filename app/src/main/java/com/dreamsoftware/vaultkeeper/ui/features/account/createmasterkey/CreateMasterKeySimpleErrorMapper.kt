package com.dreamsoftware.vaultkeeper.ui.features.account.createmasterkey

import android.content.Context
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.exception.InvalidDataException

class CreateMasterKeySimpleErrorMapper(
    private val context: Context
): IBrownieErrorMapper {
    override fun mapToMessage(ex: Throwable): String = context.getString(when(ex) {
        is InvalidDataException -> R.string.generic_form_invalid_data_provided
        else -> R.string.generic_error_exception
    })
}