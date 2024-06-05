package com.dreamsoftware.vaultkeeper.data.remote.datasource.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecureCardsRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecureCardDTO
import com.dreamsoftware.vaultkeeper.data.remote.exception.DeleteSecureCardException
import com.dreamsoftware.vaultkeeper.data.remote.exception.FetchSecureCardException
import com.dreamsoftware.vaultkeeper.data.remote.exception.FirebaseException
import com.dreamsoftware.vaultkeeper.data.remote.exception.SaveSecureCardException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

internal class SecureCardsRemoteDataSourceImpl(
    private val firebaseStore: FirebaseFirestore,
    private val dtoMapper: IBrownieMapper<SecureCardDTO, Map<String, Any?>>,
    private val dispatcher: CoroutineDispatcher
) : ISecureCardsRemoteDataSource {

    private companion object {
        const val COLLECTION_NAME = "users"
        const val SUB_COLLECTION_NAME = "secure_cards"
    }

    @Throws(SaveSecureCardException::class)
    override suspend fun save(secureCard: SecureCardDTO): Unit = withContext(dispatcher) {
        try {
            firebaseStore.collection(COLLECTION_NAME)
                .document(secureCard.userUid)
                .collection(SUB_COLLECTION_NAME)
                .document(secureCard.uid)
                .set(dtoMapper.mapInToOut(secureCard), SetOptions.merge())
                .await()
        } catch (ex: Exception) {
            throw SaveSecureCardException(
                "An error occurred when trying to save secure card information",
                ex
            )
        }
    }

    @Throws(FetchSecureCardException::class)
    override suspend fun findAllByUserUid(userUid: String): List<SecureCardDTO> =
        withContext(dispatcher) {
            try {
                val snapshot = firebaseStore.collection(COLLECTION_NAME)
                    .document(userUid)
                    .collection(SUB_COLLECTION_NAME)
                    .get()
                    .await()
                snapshot.documents.map { document ->
                    dtoMapper.mapOutToIn(
                        document.data ?: throw FetchSecureCardException("Document data is null")
                    )
                }
            } catch (ex: FirebaseException) {
                throw ex
            } catch (ex: Exception) {
                throw FetchSecureCardException(
                    "An error occurred when trying to fetch secure cards by user UID",
                    ex
                )
            }
        }

    @Throws(DeleteSecureCardException::class)
    override suspend fun deleteById(userUid: String, cardUid: String): Unit =
        withContext(dispatcher) {
            try {
                firebaseStore.collection(COLLECTION_NAME)
                    .document(userUid)
                    .collection(SUB_COLLECTION_NAME)
                    .document(cardUid)
                    .delete()
                    .await()
            } catch (ex: Exception) {
                throw DeleteSecureCardException(
                    "An error occurred when trying to delete the secure card with ID $cardUid",
                    ex
                )
            }
        }

    @Throws(DeleteSecureCardException::class)
    override suspend fun deleteAllByUserUid(userUid: String): Unit = withContext(dispatcher) {
        try {
            val batch = firebaseStore.batch()
            val documents = firebaseStore.collection(COLLECTION_NAME)
                .document(userUid)
                .collection(SUB_COLLECTION_NAME)
                .get()
                .await()
                .documents
            documents.forEach { document ->
                batch.delete(document.reference)
            }
            batch.commit().await()
        } catch (ex: Exception) {
            throw DeleteSecureCardException(
                "An error occurred when trying to delete all secure cards",
                ex
            )
        }
    }

    @Throws(FetchSecureCardException::class)
    override suspend fun findById(userUid: String, cardUid: String): SecureCardDTO =
        withContext(dispatcher) {
            try {
                val document = firebaseStore.collection(COLLECTION_NAME)
                    .document(userUid)
                    .collection(SUB_COLLECTION_NAME)
                    .document(cardUid)
                    .get()
                    .await()
                dtoMapper.mapOutToIn(
                    document.data ?: throw FetchSecureCardException("Document data is null")
                )
            } catch (ex: FirebaseException) {
                throw ex
            } catch (ex: Exception) {
                throw FetchSecureCardException(
                    "An error occurred when trying to fetch the secure card with ID $cardUid",
                    ex
                )
            }
        }
}