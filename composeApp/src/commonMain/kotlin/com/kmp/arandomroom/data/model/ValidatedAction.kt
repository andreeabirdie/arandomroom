package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidatedAction(
    val action: Action?,
    val actionFeedback: String
) {
    companion object {
        fun getDefaultValidatedAction() : ValidatedAction {
            return ValidatedAction(
                action = null,
                actionFeedback = "Some feedback about the action that cannot be null"
            )
        }
    }
}