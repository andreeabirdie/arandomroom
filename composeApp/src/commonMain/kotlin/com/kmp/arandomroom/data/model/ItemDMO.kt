package com.kmp.arandomroom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kmp.arandomroom.domain.model.ItemDTO

@Entity(
    tableName = "items",
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
data class ItemDMO(
    @PrimaryKey val id: String,
    val gameId: String,
    val roomId: String,
    val name: String,
    val description: String,
    val isInInventory: Boolean
) {
    companion object {
        fun ItemDMO.toDTO(): ItemDTO {
            return ItemDTO(
                id = id,
                name = name,
                description = description,
            )
        }
    }
}