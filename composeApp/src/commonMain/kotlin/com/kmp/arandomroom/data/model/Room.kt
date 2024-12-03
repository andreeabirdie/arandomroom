package com.kmp.arandomroom.data.model

import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Room(
    val id: String,
    val name: String,
    val description: String,
    val actions: List<Action>,
    val items: List<Item>,
    val interactableObject: List<InteractableObject>
) {
    companion object {
        fun getSchema(): Schema<JsonObject> {
            return Schema(
                name = "room",
                description = "A room in the game",
                type = FunctionType.OBJECT,
                properties = mapOf(
                    "id" to Schema(
                        name = "id",
                        description = "Unique identifier for the room",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "name" to Schema(
                        name = "name",
                        description = "Name of the room",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "description" to Schema(
                        name = "description",
                        description = "Description of the room",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "actions" to Schema(
                        name = "actions",
                        description = "List of actions available in the room",
                        type = FunctionType.ARRAY,
                        items = Action.getSchema(),
                        nullable = false
                    ),
                    "items" to Schema(
                        name = "items",
                        description = "List of items in the room",
                        type = FunctionType.ARRAY,
                        items = Item.getSchema(),
                        nullable = false
                    ),
                    "interactableObject" to Schema(
                        name = "interactableObject",
                        description = "List of interactable objects in the room",
                        type = FunctionType.ARRAY,
                        items = InteractableObject.getSchema(),
                        nullable = false
                    )
                ),
                required = listOf(
                    "id",
                    "name",
                    "description",
                    "actions",
                    "items",
                    "interactableObject"
                )
            )
        }

    }
}