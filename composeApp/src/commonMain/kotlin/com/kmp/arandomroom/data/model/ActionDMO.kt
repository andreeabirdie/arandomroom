package com.kmp.arandomroom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
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
            entity = RoomDMO::class,
            parentColumns = ["id"],
            childColumns = ["roomDestinationId"],
            onDelete = ForeignKey.SET_NULL
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
        androidx.room.Index(value = ["gameId"]),
        androidx.room.Index(value = ["roomId"]),
        androidx.room.Index(value = ["roomDestinationId"]),
        androidx.room.Index(value = ["itemId"]),
        androidx.room.Index(value = ["objectId"])
    ]
)
data class ActionDMO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gameId: String,
    val roomId: String,
    val type: String,
    val direction: String?,
    val roomDestinationId: String?,
    val itemId: String?,
    val objectId: String?
) {
    companion object {
        fun ActionDMO.toDTO(): ActionDTO {
            return ActionDTO(
                type = type,
                direction = direction,
                roomDestinationId = roomDestinationId,
                itemId = itemId,
                objectId = objectId
            )
        }
    }
}