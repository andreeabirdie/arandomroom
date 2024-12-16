package com.kmp.arandomroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kmp.arandomroom.data.model.RoomDMO

@Dao
interface RoomDao {

    @Query("SELECT * FROM rooms WHERE gameId = :gameId")
    suspend fun getAllRoomsForGame(gameId: String): List<RoomDMO>

    @Update
    suspend fun updateRoom(room: RoomDMO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createRoom(room: RoomDMO)
}