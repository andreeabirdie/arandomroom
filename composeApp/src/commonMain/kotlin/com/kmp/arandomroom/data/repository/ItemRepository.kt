package com.kmp.arandomroom.data.repository

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.model.ItemDMO

class ItemRepository(gameDatabase: GameDatabase) {

    private val itemDao = gameDatabase.getItemDao()

    suspend fun getAllItemsForRoom(roomId: String, gameId: String): List<ItemDMO> =
        itemDao.getAllItemsForRoom(roomId, gameId)

    suspend fun getInventoryItemsForGame(gameId: String): List<ItemDMO> =
        itemDao.getInventoryItemsForGame(gameId)

    suspend fun insertItem(item: ItemDMO) = itemDao.insertItem(item)

    suspend fun setItemIsInInventory(itemId: String, isInInventory: Boolean) =
        itemDao.setItemIsInInventory(itemId, isInInventory)
}