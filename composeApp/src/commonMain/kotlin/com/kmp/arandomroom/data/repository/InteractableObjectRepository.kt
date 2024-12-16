package com.kmp.arandomroom.data.repository

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.model.InteractableObjectDMO

class InteractableObjectRepository(gameDatabase: GameDatabase) {

    private val interactableObjectDao = gameDatabase.getInteractableObjectDao()

    suspend fun getAllInteractableObjectsForRoom(
        roomId: String,
        gameId: String
    ): List<InteractableObjectDMO> {
        return interactableObjectDao.getAllInteractableObjectsForRoom(roomId, gameId)
    }

    suspend fun insertInteractableObject(interactableObject: InteractableObjectDMO) {
        interactableObjectDao.createInteractableObject(interactableObject)
    }
}