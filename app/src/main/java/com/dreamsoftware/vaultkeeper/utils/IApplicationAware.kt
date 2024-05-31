package com.dreamsoftware.vaultkeeper.utils

import com.dreamsoftware.brownie.utils.IBrownieApplicationAware

interface IApplicationAware: IBrownieApplicationAware {

    fun isBiometricSupported(): Boolean
}