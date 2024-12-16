package com.kmp.arandomroom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kmp.arandomroom.domain.model.ActionDTO
import com.kmp.arandomroom.domain.model.InteractableObjectDTO
import com.kmp.arandomroom.domain.model.ItemDTO
import com.kmp.arandomroom.domain.model.RoomDTO

@Entity(
    tableName = "rooms",
    foreignKeys = [ForeignKey(
        entity = GameStateDMO::class,
        parentColumns = ["id"],
        childColumns = ["gameId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [androidx.room.Index(value = ["gameId"])]
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
            actions: List<ActionDTO>,
            items: List<ItemDTO>,
            interactableObjects: List<InteractableObjectDTO>
        ): RoomDTO {
            return RoomDTO(
                id = id,
                name = name,
                description = description,
                isVisited = isVisited,
                actions = actions,
                items = items,
                interactableObjects = interactableObjects,
            )
        }
    }
}