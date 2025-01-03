package com.kmp.arandomroom.domain.model

import com.kmp.arandomroom.data.model.ItemDMO
import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ItemDTO(
    val id: String,
    val name: String,
    val description: String,
    val descriptionOfItemPlacement: String
) {
    companion object {
        fun getSchema(): Schema<JsonObject> {
            return Schema(
                name = "item",
                description = "An item in the game",
                type = FunctionType.OBJECT,
                properties = mapOf(
                    "id" to Schema(
                        name = "id",
                        description = "Unique identifier for the item",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "name" to Schema(
                        name = "name",
                        description = "Name of the item",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "description" to Schema(
                        name = "description",
                        description = "Description of the item",
                        type = FunctionType.STRING,
                        nullable = false
                    ),
                    "descriptionOfItemPlacement" to Schema(
                        name = "descriptionOfItemPlacement",
                        description = "Description of where the item is placed in the room e.g 'There is a key on the rug'",
                        type = FunctionType.STRING,
                        nullable = false
                    )
                ),
                required = listOf("id", "name", "description", "descriptionOfItemPlacement")
            )
        }

        fun ItemDTO.toDMO(
            gameId: String,
            roomId: String,
            isInInventory: Boolean
        ): ItemDMO {
            return ItemDMO(
                id = id,
                gameId = gameId,
                roomId = roomId,
                name = name,
                description = description,
                descriptionOfItemPlacement = descriptionOfItemPlacement,
                isInInventory = isInInventory
            )
        }
    }
}