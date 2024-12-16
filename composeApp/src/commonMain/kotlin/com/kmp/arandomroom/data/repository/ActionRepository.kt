package com.kmp.arandomroom.data.repository

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.model.ActionDMO

class ActionRepository(gameDatabase: GameDatabase) {

    private val actionDao = gameDatabase.getActionDao()

    suspend fun getAllActionsForRoom(roomId: String, gameId: String) : List<ActionDMO> {
        return actionDao.getAllActionsForRoom(roomId, gameId)
    }

    suspend fun insertAction(action: ActionDMO) = actionDao.createAction(action)
}