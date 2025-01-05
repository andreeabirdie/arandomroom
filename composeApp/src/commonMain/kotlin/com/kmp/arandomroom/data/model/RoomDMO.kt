package com.kmp.arandomroom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kmp.arandomroom.domain.model.ItemDTO
import com.kmp.arandomroom.domain.model.MoveDTO
import com.kmp.arandomroom.domain.model.RoomDTO

@Entity(
    tableName = "rooms",
    foreignKeys = [ForeignKey(
        entity = GameStateDMO::class,
        parentColumns = ["id"],
        childColumns = ["gameId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["gameId"])]
)
data class RoomDMO(
    @PrimaryKey val id: String,
    val gameId: String,
    val name: String,
    val description: String,
    val isVisited: Boolean
) {
    companion object {
        fun RoomDMO.toDTO(
            moves: List<MoveDTO>,
            items: List<ItemDTO>
        ): RoomDTO {
            return RoomDTO(
                id = id,
                name = name,
                description = description,
                isVisited = isVisited,
                moves = moves,
                items = items
            )
        }
    }
}