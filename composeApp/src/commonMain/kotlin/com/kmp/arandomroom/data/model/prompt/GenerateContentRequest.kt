package com.kmp.arandomroom.data.model.prompt

import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentRequest(val contents: Content)
