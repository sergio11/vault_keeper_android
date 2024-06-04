package com.dreamsoftware.vaultkeeper.data.remote.datasource.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.remote.datasource.IAccountRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.dto.AccountDTO
import com.dreamsoftware.vaultkeeper.data.remote.exception.DeleteAccountException
import com.dreamsoftware.vaultkeeper.data.remote.exception.DeleteSecureCardException
import com.dreamsoftware.vaultkeeper.data.remote.exception.FetchAccountException
import com.dreamsoftware.vaultkeeper.data.remote.exception.FirebaseException
import com.dreamsoftware.vaultkeeper.data.remote.exception.SaveAccountException
import com.dreamsoftware.vaultkeeper.data.remote.exception.SaveSecureCardException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

internal class AccountsRemoteDataSourceImpl(
    private val firebaseStore: FirebaseFirestore,
    private val dtoMapper: IBrownieMapper<AccountDTO, Map<String, Any?>>,
    private val dispatcher: CoroutineDispatcher
) : IAccountRemoteDataSource {

    private companion object {
        const val COLLECTION_NAME = "users"
        const val SUB_COLLECTION_NAME = "accounts"
    }

    @Throws(SaveAccountException::class)
    override suspend fun save(account: AccountDTO): Unit = withContext(dispatcher) {
        try {
            firebaseStore.collection(COLLECTION_NAME)
                .document(account.userUid)
                .collection(SUB_COLLECTION_NAME)
                .document(account.uid)
                .set(dtoMapper.mapInToOut(account), SetOptions.merge())
                .await()
        } catch (ex: Exception) {
            throw SaveSecureCardException(
                "An error occurred when trying to save account information",
                ex
            )
        }
    }

    @Throws(FetchAccountException::class)
    override suspend fun getAllByUserUid(userUid: String): List<AccountDTO> =
        withContext(dispatcher) {
            try {
                val snapshot = firebaseStore.collection(COLLECTION_NAME)
                    .document(userUid)
                    .collection(SUB_COLLECTION_NAME)
                    .get()
                    .await()
                snapshot.documents.map { document ->
                    dtoMapper.mapOutToIn(
                        document.data ?: throw FetchAccountException("Document data is null")
                    )
                }
            } catch (ex: FirebaseException) {
                throw ex
            } catch (ex: Exception) {
                throw FetchAccountException(
                    "An error occurred when trying to fetch account by user UID",
                    ex
                )
            }
        }

    @Throws(DeleteAccountException::class)
    override suspend fun deleteById(userUid: String, accountUid: String): Unit =
        withContext(dispatcher) {
            try {
                firebaseStore.collection(COLLECTION_NAME)
                    .document(userUid)
                    .collection(SUB_COLLECTION_NAME)
                    .document(accountUid)
                    .delete()
                    .await()
            } catch (ex: Exception) {
                throw DeleteSecureCardException(
                    "An error occurred when trying to delete the account with ID $accountUid",
                    ex
                )
            }
        }

    @Throws(DeleteAccountException::class)
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

    @Throws(FetchAccountException::class)
    override suspend fun getById(userUid: String, accountUid: String): AccountDTO =
        withContext(dispatcher) {
            try {
                val document = firebaseStore.collection(COLLECTION_NAME)
                    .document(userUid)
                    .collection(SUB_COLLECTION_NAME)
                    .document(accountUid)
                    .get()
                    .await()
                dtoMapper.mapOutToIn(
                    document.data ?: throw FetchAccountException("Document data is null")
                )
            } catch (ex: FirebaseException) {
                throw ex
            } catch (ex: Exception) {
                throw FetchAccountException(
                    "An error occurred when trying to fetch the account with ID $accountUid",
                    ex
                )
            }
        }
}