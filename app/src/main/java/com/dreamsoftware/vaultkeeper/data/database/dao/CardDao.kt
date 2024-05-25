package com.dreamsoftware.vaultkeeper.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(cardEntity: CardEntity)

    @Update
    suspend fun updateCard(cardEntity: CardEntity)

    @Delete
    suspend fun deleteCard(cardEntity: CardEntity)

    @Query("SELECT * FROM card ORDER BY id ASC")
    fun getAllCards(): Flow<List<CardEntity>>

    @Query("SELECT * FROM `card` WHERE id = :id")
    fun getCardsById(id: Int): Flow<CardEntity>

    @Query("DELETE FROM card")
    fun deleteAllCards()
}