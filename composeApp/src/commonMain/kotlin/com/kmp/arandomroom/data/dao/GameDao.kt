package com.kmp.arandomroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kmp.arandomroom.data.model.GameStateDMO

@Dao
interface GameDao {

    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<GameStateDMO>

    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun getGameById(gameId: String): GameStateDMO

    @Query("UPDATE games SET currentRoom = :currentRoomId WHERE id = :gameId")
    suspend fun updateGameState(gameId: String, currentRoomId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameStateDMO)

    @Query("DELETE FROM games WHERE id = :gameId")
    suspend fun deleteGame(gameId: String)
}