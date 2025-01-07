package com.kmp.arandomroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kmp.arandomroom.data.model.RoomDMO

@Dao
interface RoomDao {

    @Query("SELECT * FROM rooms WHERE gameId = :gameId")
    suspend fun getAllRoomsForGame(gameId: String): List<RoomDMO>

    @Query("SELECT * FROM rooms WHERE id = :roomId")
    suspend fun getRoom(roomId: String): RoomDMO

    @Query("UPDATE rooms SET isVisited = :isVisited WHERE id = :roomId")
    suspend fun setRoomIsVisited(roomId: String, isVisited: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: RoomDMO)

    @Query("UPDATE rooms SET description = :description WHERE id = :roomId and gameId = :gameId")
    suspend fun updateRoomDescription(gameId: String, roomId: String, description: String)
}