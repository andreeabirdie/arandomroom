package com.kmp.arandomroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kmp.arandomroom.data.model.ObjectDMO

@Dao
interface ObjectDao {

    @Query("SELECT * FROM objects WHERE roomId = :roomId and gameId = :gameId")
    suspend fun getAllObjectsForRoom(
        roomId: String,
        gameId: String
    ): List<ObjectDMO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createObject(objectDMO: ObjectDMO)
}