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
) {
    companion object {
        fun getDefaultRoom() : Room {
            return Room(
                id = "",
                name = "",
                description = "",
                actions = listOf(
                    Action.Move(
                        direction = "",
                        roomId = ""
                    ),
                    Action.PickUp(
                        itemId = ""
                    ),
                    Action.Use(
                        itemId = "",
                        objectId = ""
                    ),
                    Action.Open(
                        objectId = ""
                    )
                ),
                items = listOf(Item.getDefaultItem()),
                interactableObject = listOf(InteractableObject.getDefaultInteractableObject())
            )
        }
    }
}