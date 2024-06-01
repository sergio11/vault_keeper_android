package com.dreamsoftware.vaultkeeper.ui.core.components.fab

import androidx.annotation.StringRes

/**
 * Represents an item for a Floating Action Button (FAB) with an icon and label.
 * */

data class FabButtonItem(
    val id: Int,
    val iconRes: Int,
    val label: String? = null,
    @StringRes val labelRes: Int? = null
)