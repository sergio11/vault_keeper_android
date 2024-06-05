package com.dreamsoftware.vaultkeeper.domain.validation.impl

import com.dreamsoftware.vaultkeeper.domain.model.SaveMasterKeyBO
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.vaultkeeper.domain.validation.ISaveMasterKeyValidationMessagesResolver

internal class SaveMasterKeyValidatorImpl(
    private val messagesResolver: ISaveMasterKeyValidationMessagesResolver
): IBusinessEntityValidator<SaveMasterKeyBO> {

    override fun validate(entity: SaveMasterKeyBO): Map<String, String> = buildMap {
        with(entity) {
            if (key.isEmpty() && confirmKey.isEmpty()) {
                put(SaveMasterKeyBO.FIELD_KEY, messagesResolver.getKeyEmptyError())
            }
            if (key != confirmKey) {
                put(SaveMasterKeyBO.FIELD_KEY, messagesResolver.getKeyMismatchError())
            }
            if (key.length < 6) {
                put(SaveMasterKeyBO.FIELD_KEY, messagesResolver.getKeyIncorrectLengthError())
            }
        }
    }
}