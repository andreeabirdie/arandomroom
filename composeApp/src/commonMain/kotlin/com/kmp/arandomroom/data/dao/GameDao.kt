package com.kmp.arandomroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kmp.arandomroom.data.model.GameStateDMO

@Dao
interface GameDao {

    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<GameStateDMO>

    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun getGameById(gameId: String): GameStateDMO

    @Update
    suspend fun updateGame(game: GameStateDMO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createGame(game: GameStateDMO)

    @Query("DELETE FROM games WHERE id = :gameId")
    suspend fun deleteGame(gameId: String)
}