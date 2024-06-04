package com.dreamsoftware.vaultkeeper.data.remote.datasource.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecureCardsRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecureCardDTO
import com.dreamsoftware.vaultkeeper.data.remote.exception.SaveSecureCardException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

internal class SecureCardsRemoteDataSourceImpl(
    private val firebaseStore: FirebaseFirestore,
    private val mapper: IBrownieMapper<SecureCardDTO, Map<String, Any?>>,
    private val dispatcher: CoroutineDispatcher
): ISecureCardsRemoteDataSource {

    private companion object {
        const val COLLECTION_NAME = "secure_cards"
    }

    @Throws(SaveSecureCardException::class)
    override suspend fun save(secureCard: SecureCardDTO): Unit = withContext(dispatcher) {
        try {
            firebaseStore.collection(COLLECTION_NAME)
                .document(secureCard.userUid)
                .set(mapper.mapInToOut(secureCard), SetOptions.merge())
                .await()
        } catch (ex: Exception) {
            throw SaveSecureCardException(
                "An error occurred when trying to save secure card information",
                ex
            )
        }
    }
}