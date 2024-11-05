package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Content(val parts: List<Part>)
