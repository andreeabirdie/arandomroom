package com.kmp.arandomroom.domain.model

import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class GeneratedGame(
    val title: String,
    val currentRoom: String,
    val endRoom: String,
    val rooms: List<RoomDTO>
) {
    companion object {
        fun getSchema(): Schema<JsonObject> {
            return Schema(
                name = "generatedGame",
                description = "A generated game",
                type = FunctionType.OBJECT,
                properties = mapOf(
                    "title" to Schema(
                        name = "title",
                        description = "Game id",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "currentRoom" to Schema(
                        name = "currentRoom",
                        description = "Current room id",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "endRoom" to Schema(
                        name = "endRoom",
                        description = "End room id",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "rooms" to Schema(
                        name = "rooms",
                        description = "List of rooms in the game",
                        type = FunctionType.ARRAY,
                        items = RoomDTO.getSchema(),
                        nullable = false
                    )
                ),
                required = listOf("currentRoom", "endRoom", "rooms")
            )
        }
    }
}