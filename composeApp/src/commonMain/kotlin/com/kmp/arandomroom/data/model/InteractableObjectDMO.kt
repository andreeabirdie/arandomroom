package com.kmp.arandomroom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kmp.arandomroom.domain.model.InteractableObjectDTO

@Entity(
    tableName = "interactableObjects",
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
            childColumns = ["requiredItem"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        androidx.room.Index(value = ["gameId"]),
        androidx.room.Index(value = ["roomId"]),
        androidx.room.Index(value = ["requiredItem"])
    ]
)
data class InteractableObjectDMO(
    @PrimaryKey val id: String,
    val gameId: String,
    val roomId: String,
    val name: String,
    val description: String,
    val requiredItem: String?
) {
    companion object {
        fun InteractableObjectDMO.toDTO(): InteractableObjectDTO {
            return InteractableObjectDTO(
                id = id,
                name = name,
                description = description,
                requiredItem = requiredItem
            )
        }
    }
}