package com.dreamsoftware.vaultkeeper.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dreamsoftware.vaultkeeper.data.database.entity.SecureCardEntity

@Dao
interface SecureCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(secureCardEntity: SecureCardEntity)

    @Update
    suspend fun updateCard(secureCardEntity: SecureCardEntity)

    @Delete
    suspend fun deleteCard(secureCardEntity: SecureCardEntity)

    @Query("SELECT * FROM secure_cards ORDER BY uid ASC")
    fun getAllCards(): List<SecureCardEntity>

    @Query("SELECT * FROM `secure_cards` WHERE uid = :uid")
    fun getCardsById(uid: String): SecureCardEntity?

    @Query("DELETE FROM secure_cards")
    fun deleteAllCards()
}