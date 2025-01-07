package com.kmp.arandomroom.domain.model

import com.kmp.arandomroom.data.model.RoomDMO
import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RoomDTO(
    val id: String,
    val name: String,
    val description: String,
    val isVisited: Boolean,
    val items: List<ItemDTO>,
    val moves: List<MoveDTO>
) {
    companion object {
        fun getSchema(): Schema<JsonObject> {
            return Schema(
                name = "room",
                description = "A room in the game.",
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
                        description = "Description of the room.",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "isVisited" to Schema(
                        name = "isVisited",
                        description = "Always set this to false",
                        type = FunctionType.BOOLEAN,
                        nullable = false
                    ),
                    "moves" to Schema(
                        name = "moves",
                        description = "List of moves available in the room",
                        type = FunctionType.ARRAY,
                        items = MoveDTO.getSchema(),
                        nullable = false
                    ),
                    "items" to Schema(
                        name = "items",
                        description = "List of items in the room that can be picked up by the user.",
                        type = FunctionType.ARRAY,
                        items = ItemDTO.getSchema(),
                        nullable = false
                    )
                ),
                required = listOf("id", "name", "description", "isVisited", "moves", "items")
            )
        }

        fun RoomDTO.toDMO(gameId: String): RoomDMO {
            return RoomDMO(
                id = id,
                gameId = gameId,
                name = name,
                description = description,
                initialDescription = description,
                isVisited = isVisited
            )
        }
    }
}