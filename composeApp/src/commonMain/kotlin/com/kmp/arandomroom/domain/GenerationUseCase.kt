package com.kmp.arandomroom.domain

import com.kmp.arandomroom.BuildKonfig
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import dev.shreyaspatil.ai.client.generativeai.type.content
import dev.shreyaspatil.ai.client.generativeai.type.generationConfig
import kotlinx.serialization.json.JsonObject
import org.koin.core.component.KoinComponent

class GenerationUseCase(
    schema: Schema<JsonObject>
) : KoinComponent {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-pro",
        apiKey = BuildKonfig.GEMINI_API_KEY,
        generationConfig = generationConfig {
            responseMimeType = "application/json"
            responseSchema = schema
        }
    )

    suspend fun generateResponse(prompt: String): String? {
        val inputContent = content { text(prompt) }

        return try {
            val response = generativeModel.generateContent(inputContent).text
            response
        } catch (e: Exception) {
            println("qwerty $e")
            throw e
        }

    }
}