package com.kmp.arandomroom.data.repository

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.model.RoomDMO

class RoomRepository(gameDatabase: GameDatabase) {

    private val roomDao = gameDatabase.getRoomDao()

    suspend fun getAllRoomsForGame(gameId: String) = roomDao.getAllRoomsForGame(gameId)

    suspend fun getRoom(roomId: String) = roomDao.getRoom(roomId)

    suspend fun insertRoom(roomDMO: RoomDMO) = roomDao.createRoom(roomDMO)

    suspend fun setRoomIsVisited(roomId: String, isVisited: Boolean) {
        roomDao.setRoomIsVisited(roomId, isVisited)
    }

    suspend fun updateRoomDescription(gameId: String, roomId: String, description: String) {
        roomDao.updateRoomDescription(gameId, roomId, description)
    }
}