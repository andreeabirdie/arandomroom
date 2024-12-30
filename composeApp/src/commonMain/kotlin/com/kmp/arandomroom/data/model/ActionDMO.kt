package com.kmp.arandomroom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kmp.arandomroom.domain.model.ActionDTO

@Entity(
    tableName = "actions",
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
        ),
        ForeignKey(
            entity = ItemDMO::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = InteractableObjectDMO::class,
            parentColumns = ["id"],
            childColumns = ["objectId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["gameId"]),
        Index(value = ["roomId"]),
        Index(value = ["itemId"]),
        Index(value = ["objectId"])
    ]
)
data class ActionDMO(
    @PrimaryKey val id: String,
    val gameId: String,
    val roomId: String,
    val type: String,
    val itemId: String?,
    val objectId: String?
) {
    companion object {
        fun ActionDMO.toDTO(): ActionDTO {
            return ActionDTO(
                id = id,
                type = type,
                itemId = itemId,
                objectId = objectId
            )
        }
    }
}