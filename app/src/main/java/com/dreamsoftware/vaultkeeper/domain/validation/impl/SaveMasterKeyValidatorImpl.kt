package com.dreamsoftware.vaultkeeper.domain.validation.impl

import com.dreamsoftware.vaultkeeper.domain.model.SaveSecretBO
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.vaultkeeper.domain.validation.ISaveMasterKeyValidationMessagesResolver

internal class SaveMasterKeyValidatorImpl(
    private val messagesResolver: ISaveMasterKeyValidationMessagesResolver
): IBusinessEntityValidator<SaveSecretBO> {

    override fun validate(entity: SaveSecretBO): Map<String, String> = buildMap {
        with(entity) {
            if (key.isEmpty() && confirmKey.isEmpty()) {
                put(SaveSecretBO.FIELD_KEY, messagesResolver.getKeyEmptyError())
            }
            if (key != confirmKey) {
                put(SaveSecretBO.FIELD_KEY, messagesResolver.getKeyMismatchError())
            }
            if (key.length < 6) {
                put(SaveSecretBO.FIELD_KEY, messagesResolver.getKeyIncorrectLengthError())
            }
        }
    }
}