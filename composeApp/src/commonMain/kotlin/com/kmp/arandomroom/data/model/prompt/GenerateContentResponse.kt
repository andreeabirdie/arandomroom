package com.kmp.arandomroom.data.model.prompt

import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentResponse(
    val error: Error? = null,
    val candidates: List<Candidate>? = null
)
