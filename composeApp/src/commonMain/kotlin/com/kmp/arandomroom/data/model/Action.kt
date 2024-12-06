package com.kmp.arandomroom.data.model

import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Action(
    val type: String,
    val direction: String?,
    val roomId: String?,
    val itemId: String?,
    val objectId: String?
) {
    companion object {
        fun Action.getActionType(): ActionType? {
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
                    "type" to Schema(
                        name = "type",
                        description = "Type of action the user can perform. Move is used when navigating to a different room, PickUp is used when picking up an item from the room, Use is used when using an item from the inventory, Open is used when opening an object in the room",
                        type = FunctionType.STRING,
                        enum = listOf("Move", "PickUp", "Use", "Open"),
                        nullable = false
                    ),
                    "direction" to Schema(
                        name = "direction",
                        description = "Direction to move (only for Move action), null otherwise",
                        type = FunctionType.STRING,
                        nullable = true
                    ),
                    "roomId" to Schema(
                        name = "roomId",
                        description = "Room ID to move to (only for Move action), null otherwise",
                        type = FunctionType.STRING,
                        nullable = true
                    ),
                    "itemId" to Schema(
                        name = "itemId",
                        description = "Item ID (used for PickUp or Use actions), null otherwise",
                        type = FunctionType.STRING,
                        nullable = true
                    ),
                    "objectId" to Schema(
                        name = "objectId",
                        description = "Object ID to interact with (only for Use or Open actions), null otherwise",
                        type = FunctionType.STRING,
                        nullable = true
                    )
                ),
                required = listOf("type", "direction", "roomId", "itemId", "objectId")
            )
        }
    }
}