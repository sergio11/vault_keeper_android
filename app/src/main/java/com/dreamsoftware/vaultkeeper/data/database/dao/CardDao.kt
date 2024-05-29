package com.dreamsoftware.vaultkeeper.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(cardEntity: CardEntity): Int

    @Update
    suspend fun updateCard(cardEntity: CardEntity)

    @Delete
    suspend fun deleteCard(cardEntity: CardEntity)

    @Query("SELECT * FROM secure_cards ORDER BY id ASC")
    fun getAllCards(): List<CardEntity>

    @Query("SELECT * FROM `secure_cards` WHERE id = :id")
    fun getCardsById(id: Int): CardEntity?

    @Query("DELETE FROM secure_cards")
    fun deleteAllCards()
}