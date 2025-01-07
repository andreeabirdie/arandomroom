package com.kmp.arandomroom.data.repository

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.model.GameStateDMO

class GameRepository(gameDatabase: GameDatabase) {

    private val gameDao = gameDatabase.getGameDao()

    suspend fun getAllGames(): List<GameStateDMO> = gameDao.getAllGames()

    suspend fun getGameById(gameId: String): GameStateDMO = gameDao.getGameById(gameId)

    suspend fun insertGame(game: GameStateDMO) = gameDao.insertGame(game)

    suspend fun updateGameState(gameId: String, currentRoomId: String) =
        gameDao.updateGameState(gameId, currentRoomId)

    suspend fun deleteGame(gameId: String) = gameDao.deleteGame(gameId)
}