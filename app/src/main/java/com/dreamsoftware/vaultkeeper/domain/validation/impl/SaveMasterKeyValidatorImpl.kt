package com.dreamsoftware.vaultkeeper.domain.validation.impl

import com.dreamsoftware.vaultkeeper.domain.model.SaveSecretBO
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.vaultkeeper.domain.validation.ISaveMasterKeyValidationMessagesResolver

internal class SaveMasterKeyValidatorImpl(
    private val messagesResolver: ISaveMasterKeyValidationMessagesResolver
): IBusinessEntityValidator<SaveSecretBO> {

    private companion object {
        const val MASTER_KEY_LENGTH = 8
        const val SECRET_SALT_LENGTH = 20
    }

    override fun validate(entity: SaveSecretBO): Map<String, String> = buildMap {
        with(entity) {
            if (key.isEmpty()) {
                put(SaveSecretBO.FIELD_KEY, messagesResolver.getKeyEmptyError())
            }
            if (key.length < MASTER_KEY_LENGTH) {
                put(SaveSecretBO.FIELD_KEY, messagesResolver.getKeyIncorrectLengthError())
            }
            if (confirmKey.isEmpty()) {
                put(SaveSecretBO.FIELD_CONFIRM_KEY, messagesResolver.getKeyEmptyError())
            }
            if (key != confirmKey) {
                put(SaveSecretBO.FIELD_CONFIRM_KEY, messagesResolver.getKeyMismatchError())
            }
            if(salt.length < SECRET_SALT_LENGTH) {
                put(SaveSecretBO.FIELD_SALT_KEY, messagesResolver.getSaltIncorrectLengthError())
            }
        }
    }
}