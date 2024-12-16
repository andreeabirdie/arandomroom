package com.kmp.arandomroom.data.repository

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.model.RoomDMO

class RoomRepository(gameDatabase: GameDatabase) {

    private val roomDao = gameDatabase.getRoomDao()

    suspend fun getAllRoomsForGame(gameId: String) = roomDao.getAllRoomsForGame(gameId)

    suspend fun insertRoom(roomDMO: RoomDMO) = roomDao.createRoom(roomDMO)

    suspend fun updateRoom(roomDMO: RoomDMO) = roomDao.updateRoom(roomDMO)
}