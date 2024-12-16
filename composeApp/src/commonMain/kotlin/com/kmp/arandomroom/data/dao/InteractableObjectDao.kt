package com.kmp.arandomroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kmp.arandomroom.data.model.InteractableObjectDMO

@Dao
interface InteractableObjectDao {

    @Query("SELECT * FROM interactableObjects WHERE roomId = :roomId and gameId = :gameId")
    suspend fun getAllInteractableObjectsForRoom(
        roomId: String,
        gameId: String
    ): List<InteractableObjectDMO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createInteractableObject(interactableObject: InteractableObjectDMO)
}