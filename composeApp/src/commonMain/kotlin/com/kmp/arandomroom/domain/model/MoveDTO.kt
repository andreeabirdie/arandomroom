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
    val roomDestinationId: String
) {
    companion object {
        fun getSchema(): Schema<JsonObject> {
            return Schema(
                name = "move",
                description = "A move made by the player. Moves must are bidirectional. If I move west into a room then the room I moved into must have a move east into the room I came from.",
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
                        description = "Direction of the move",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "roomDestinationId" to Schema(
                        name = "roomDestinationId",
                        description = "Room ID of the destination",
                        type = FunctionType.STRING,
                        nullable = false
                    )
                )
            )
        }

        fun MoveDTO.toDMO(gameId: String, roomId: String): MoveDMO {
            return MoveDMO(
                id = id,
                gameId = gameId,
                roomId = roomId,
                direction = direction,
                roomDestinationId = roomDestinationId
            )
        }
    }
}