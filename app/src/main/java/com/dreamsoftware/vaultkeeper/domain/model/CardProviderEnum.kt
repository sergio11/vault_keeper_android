package com.dreamsoftware.vaultkeeper.domain.model

enum class CardProviderEnum {
    VISA, MASTERCARD, AMERICAN_EXPRESS, RUPAY, DINERS_CLUB, OTHER;

    companion object {
        @JvmStatic
        fun fromName(name: String?) = name?.let {
            runCatching { valueOf(name) }.getOrDefault(OTHER)
        } ?: OTHER
    }
}