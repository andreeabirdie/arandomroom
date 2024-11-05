package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentResponse(
    val error: Error? = null,
    val candidates: List<Candidate>? = null
)
