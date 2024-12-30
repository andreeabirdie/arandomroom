package com.kmp.arandomroom.domain.model

import dev.shreyaspatil.ai.client.generativeai.type.FunctionType
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ValidatedAction(
    val actionId: String?,
    val actionFeedback: String
) {
    companion object {
        fun getSchema() : Schema<JsonObject> {
            return Schema(
                name = "validatedAction",
                description = "A validated action transformed from user input",
                type = FunctionType.OBJECT,
                properties = mapOf(
                    "actionId" to Schema(
                        name = "action",
                        description = "Id of the identified action to be performed",
                        type = FunctionType.STRING,
                        nullable = true
                    ),
                    "actionFeedback" to Schema(
                        name = "actionFeedback",
                        description = "Feedback message for the action if it's not an existing action",
                        type = FunctionType.STRING
                    )
                ),
                required = listOf("actionId", "actionFeedback")
            )
        }

        fun getErrorMessage() : ValidatedAction {
            return ValidatedAction(null, "Something went wrong. Please try again.")
        }
    }
}