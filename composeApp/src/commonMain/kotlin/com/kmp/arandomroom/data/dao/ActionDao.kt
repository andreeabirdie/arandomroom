package com.kmp.arandomroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kmp.arandomroom.data.model.ActionDMO

@Dao
interface ActionDao {

    @Query("SELECT * FROM actions WHERE roomId = :roomId and gameId = :gameId")
    suspend fun getAllActionsForRoom(roomId: String, gameId: String): List<ActionDMO>

    @Insert
    suspend fun createAction(action: ActionDMO)
}