package com.kmp.arandomroom.data.repository

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.model.MoveDMO

class MoveRepository(gameDatabase: GameDatabase) {

    private val moveDao = gameDatabase.getMoveDao()

    suspend fun getMovesForRoom(gameId: String, roomId: String) : List<MoveDMO>{
        return moveDao.getMovesForRoom(gameId, roomId)
    }

    suspend fun insertMove(moveDMO: MoveDMO) = moveDao.createMove(moveDMO)
}