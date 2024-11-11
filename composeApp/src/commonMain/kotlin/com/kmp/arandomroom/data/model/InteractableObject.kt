package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class InteractableObject(
    val id: String,
    val name: String,
    val description: String,
    val requiredItem: String? = null
)