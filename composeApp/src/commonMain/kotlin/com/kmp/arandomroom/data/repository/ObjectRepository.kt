package com.kmp.arandomroom.data.repository

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.model.ObjectDMO

class ObjectRepository(gameDatabase: GameDatabase) {

    private val objectDao = gameDatabase.getObjectDao()

    suspend fun getAllObjectsForRoom(
        roomId: String,
        gameId: String
    ): List<ObjectDMO> {
        return objectDao.getAllObjectsForRoom(roomId, gameId)
    }

    suspend fun insertObject(objectDMO: ObjectDMO) {
        objectDao.createObject(objectDMO)
    }
}