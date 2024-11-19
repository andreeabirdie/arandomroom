package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Item (
    val id: String = "",
    val name: String = "",
    val description: String = ""
) {
    companion object {
        fun getDefaultItem() : Item {
            return Item(
                id = "",
                name = "",
                description = ""
            )
        }
    }
}