package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Action {

    @Serializable
    data class Move(
        val direction: String,
        val roomId: String,
        val feedback: String
    ) : Action()

    @Serializable
    data class PickUp(
        val itemId: String,
        val feedback: String
    ) : Action()

    @Serializable
    data class Use(
        val itemId: String,
        val objectId: String,
        val feedback: String
    ) : Action()

    @Serializable
    data class Open(
        val objectId: String,
        val feedback: String
    ) : Action()
}