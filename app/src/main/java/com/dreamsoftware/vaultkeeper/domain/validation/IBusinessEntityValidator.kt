package com.dreamsoftware.vaultkeeper.domain.validation

interface IBusinessEntityValidator<in T> {
    fun validate(entity: T): Map<String, String>
}