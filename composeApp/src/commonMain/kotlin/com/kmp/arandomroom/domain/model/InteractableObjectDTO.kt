package com.kmp.arandomroom.domain.model

import com.kmp.arandomroom.data.model.InteractableObjectDMO
import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class InteractableObjectDTO(
    val id: String,
    val name: String,
    val description: String,
    val requiredItem: String?
) {
    companion object {
        fun getSchema() : Schema<JsonObject> {
            return Schema(
                name = "interactableObject",
                description = "An interactable object in the game",
                type = FunctionType.OBJECT,
                properties = mapOf(
                    "id" to Schema(
                        name = "id",
                        description = "Unique identifier for the object",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "name" to Schema(
                        name = "name",
                        description = "Name of the object",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "description" to Schema(
                        name = "description",
                        description = "Description of the object",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "requiredItem" to Schema(
                        name = "requiredItem",
                        description = "Id of the item required to interact with the object, null if no item is required",
                        type = FunctionType.STRING,
                        nullable = true
                    )
                ),
                required = listOf("id", "name", "description", "requiredItem")
            )
        }

        fun InteractableObjectDTO.toDMO(
            gameId: String,
            roomId: String
        ) : InteractableObjectDMO {
            return InteractableObjectDMO(
                id = id,
                gameId = gameId,
                roomId = roomId,
                name = name,
                description = description,
                requiredItem = requiredItem
            )
        }
    }
}