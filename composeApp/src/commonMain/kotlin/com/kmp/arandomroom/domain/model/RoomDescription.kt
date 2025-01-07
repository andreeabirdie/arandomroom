package com.kmp.arandomroom.domain.model

import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable

@Serializable
data class RoomDescription(
    val description: String
) {
    companion object {
        fun getSchema(): Schema<String> {
            return Schema(
                name = "roomDescription",
                description = "Description of the room.",
                type = FunctionType.STRING,
                nullable = false
            )
        }
    }
}