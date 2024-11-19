package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class InteractableObject(
    val id: String,
    val name: String,
    val description: String,
    val requiredItem: String?
) {
    companion object {
        fun getDefaultInteractableObject() : InteractableObject {
            return InteractableObject(
                id = "",
                name = "",
                description = "",
                requiredItem = "nullable"
            )
        }
    }
}