package com.kmp.arandomroom.data.repository

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.model.ItemDMO

class ItemRepository(gameDatabase: GameDatabase) {

    private val itemRepository = gameDatabase.getItemDao()

    suspend fun getAllItemsForRoom(roomId: String, gameId: String) : List<ItemDMO> {
        return itemRepository.getAllItemsForRoom(roomId, gameId)
    }

    suspend fun getInventoryItemsForGame(gameId: String) : List<ItemDMO> {
        return itemRepository.getInventoryItemsForGame(gameId)
    }

    suspend fun insertItem(item: ItemDMO) {
        itemRepository.createItem(item)
    }
}