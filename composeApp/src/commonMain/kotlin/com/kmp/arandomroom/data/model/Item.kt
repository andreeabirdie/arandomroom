package com.kmp.arandomroom.data.model

import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Item (
    val id: String = "",
    val name: String = "",
    val description: String = ""
) {
    companion object {
        fun getSchema() : Schema<JsonObject> {
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
                    )
                ),
                required = listOf("id", "name", "description")
            )
        }
    }
}