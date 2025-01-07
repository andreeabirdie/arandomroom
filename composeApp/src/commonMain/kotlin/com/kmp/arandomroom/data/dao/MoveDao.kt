package com.kmp.arandomroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kmp.arandomroom.data.model.MoveDMO

@Dao
interface MoveDao {

    @Query("SELECT * FROM moves WHERE gameId = :gameId and roomId = :roomId")
    suspend fun getMovesForRoom(gameId: String, roomId: String): List<MoveDMO>

    @Insert
    suspend fun insertMove(moveDMO: MoveDMO)
}