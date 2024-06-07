package com.dreamsoftware.vaultkeeper.ui.features.home

import android.content.Context
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.vaultkeeper.R

class HomeSimpleErrorMapper(
    private val context: Context
): IBrownieErrorMapper {
    override fun mapToMessage(ex: Throwable): String = context.getString(R.string.generic_error_exception)
}