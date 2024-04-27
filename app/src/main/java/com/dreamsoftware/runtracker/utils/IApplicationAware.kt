package com.dreamsoftware.runtracker.utils

import androidx.annotation.StringRes

interface IApplicationAware {
    fun getString(@StringRes stringRes: Int): String
}