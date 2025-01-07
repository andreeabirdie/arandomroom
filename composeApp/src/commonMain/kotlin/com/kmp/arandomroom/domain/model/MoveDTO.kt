package com.kmp.arandomroom.domain.model

import com.kmp.arandomroom.data.model.MoveDMO
import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class MoveDTO(
    val id: String,
    val direction: String,
    val roomDestinationId: String,
    val requiredItem: String?
) {
    companion object {
        fun getSchema(): Schema<JsonObject> {
            return Schema(
                name = "move",
                description = "A move made by the player. Make sure that for each move from room A to room B, there is a corresponding move from room B to room A in the opposite direction",
                type = FunctionType.OBJECT,
                properties = mapOf(
                    "id" to Schema(
                        name = "id",
                        description = "Unique identifier for the move. Has to be unique within the game",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "direction" to Schema(
                        name = "direction",
                        description = "Direction of the move. Has to be one of the following: north, east, south, west",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "roomDestinationId" to Schema(
                        name = "roomDestinationId",
                        description = "Room ID of the destination",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "requiredItem" to Schema(
                        name = "requiredItem",
                        description = "Item required to make the move or null if an item isn't needed. Can be for example a key to open a door.",
                        type = FunctionType.STRING,
                        nullable = true
                    )
                ),
                required = listOf("id", "direction", "roomDestinationId", "requiredItem")
            )
        }

        fun MoveDTO.toDMO(gameId: String, roomId: String): MoveDMO {
            return MoveDMO(
                id = id,
                gameId = gameId,
                roomId = roomId,
                direction = direction,
                roomDestinationId = roomDestinationId,
                requiredItem = requiredItem
            )
        }
    }
}