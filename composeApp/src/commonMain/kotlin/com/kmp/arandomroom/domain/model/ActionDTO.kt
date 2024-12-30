package com.kmp.arandomroom.domain.model

import com.kmp.arandomroom.data.model.ActionDMO
import com.kmp.arandomroom.data.model.ActionType
import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ActionDTO(
    val id: String,
    val type: String,
    val itemId: String?,
    val objectId: String?
) {
    companion object {
        fun ActionDTO.getActionType(): ActionType? {
            return ActionType.entries.find { actionType ->
                actionType.name.equals(type, ignoreCase = true)
            }
        }

        fun getSchema(): Schema<JsonObject> {
            return Schema(
                name = "action",
                description = "An action that can be performed",
                type = FunctionType.OBJECT,
                properties = mapOf(
                    "id" to Schema(
                        name = "id",
                        description = "Unique identifier for the action",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "type" to Schema(
                        name = "type",
                        description = "Type of action the user can perform. PickUp is used when picking up an item from the room, Use is used when using an item from the inventory, Interact is used when interacting with an object in the room",
                        type = FunctionType.STRING,
                        enum = listOf("PickUp", "Use", "Interact"),
                        nullable = false
                    ),
                    "itemId" to Schema(
                        name = "itemId",
                        description = "Item ID (used for PickUp or Use actions), null otherwise",
                        type = FunctionType.STRING,
                        nullable = true
                    ),
                    "objectId" to Schema(
                        name = "objectId",
                        description = "Object ID to interact with (only for Use or Interact actions), null otherwise",
                        type = FunctionType.STRING,
                        nullable = true
                    )
                ),
                required = listOf("id", "type", "itemId", "objectId")
            )
        }

        fun ActionDTO.toDMO(
            gameId: String,
            roomId: String
        ): ActionDMO {
            return ActionDMO(
                id = id,
                gameId = gameId,
                roomId = roomId,
                type = type,
                itemId = itemId,
                objectId = objectId
            )
        }
    }
}