package com.kmp.arandomroom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kmp.arandomroom.domain.model.MoveDTO

@Entity(
    tableName = "moves",
    foreignKeys = [
        ForeignKey(
            entity = GameStateDMO::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoomDMO::class,
            parentColumns = ["id"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["gameId"]),
        Index(value = ["roomId"]),
    ]
)
data class MoveDMO(
    @PrimaryKey val id: String,
    val gameId: String,
    val roomId: String,
    val direction: String,
    val roomDestinationId: String,
    val requiredItem: String?
) {
    companion object {
        fun MoveDMO.toDTO(): MoveDTO {
            return MoveDTO(
                id = id,
                direction = direction,
                roomDestinationId = roomDestinationId,
                requiredItem = requiredItem
            )
        }
    }
}