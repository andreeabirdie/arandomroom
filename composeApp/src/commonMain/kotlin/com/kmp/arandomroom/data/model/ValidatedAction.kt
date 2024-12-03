package com.kmp.arandomroom.data.model

import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ValidatedAction(
    val action: Action?,
    val actionFeedback: String
) {
    companion object {
        fun getSchema() : Schema<JsonObject> {
            return Schema(
                name = "validatedAction",
                description = "A validated action transformed from user input",
                type = FunctionType.OBJECT,
                properties = mapOf(
                    "action" to Schema(
                        name = "action",
                        description = "Action object if valid action was provided, null otherwise",
                        type = FunctionType.OBJECT,
                        properties = Action.getSchema().properties,
                        required = Action.getSchema().required,
                        nullable = true
                    ),
                    "actionFeedback" to Schema(
                        name = "actionFeedback",
                        description = "Feedback message for the action",
                        type = FunctionType.STRING,
                        nullable = true
                    )
                ),
                required = listOf("action", "actionFeedback")
            )
        }
    }
}