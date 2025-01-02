package com.kmp.arandomroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kmp.arandomroom.data.model.ItemDMO

@Dao
interface ItemDao {

    @Query("SELECT * FROM items WHERE roomId = :roomId and gameId = :gameId and isInInventory = 0")
    suspend fun getAllItemsForRoom(roomId: String, gameId: String): List<ItemDMO>

    @Query("SELECT * FROM items WHERE gameId = :gameId and isInInventory = 1")
    suspend fun getInventoryItemsForGame(gameId: String): List<ItemDMO>

    @Update
    suspend fun updateItem(item: ItemDMO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createItem(item: ItemDMO)

    @Query("UPDATE items SET isInInventory = 1 WHERE id = :itemId")
    suspend fun setItemIsInInventory(itemId: String)
}