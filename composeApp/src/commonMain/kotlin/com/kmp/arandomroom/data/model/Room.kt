package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val id: String,
    val name: String,
    val description: String,
    val actions: List<Action>,
    val items: List<Item>,
    val interactableObject: List<InteractableObject>
)