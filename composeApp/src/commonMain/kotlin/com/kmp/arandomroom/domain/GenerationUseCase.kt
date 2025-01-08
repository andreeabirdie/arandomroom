package com.kmp.arandomroom.domain

import com.kmp.arandomroom.BuildKonfig
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import dev.shreyaspatil.ai.client.generativeai.type.content
import dev.shreyaspatil.ai.client.generativeai.type.generationConfig
import io.github.aakira.napier.Napier
import org.koin.core.component.KoinComponent

class GenerationUseCase(
    schema: Schema<*>
) : KoinComponent {

    private val generativeModel = GenerativeModel(
        modelName = BuildKonfig.MODEL_NAME,
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
            Napier.e("$e ${e.cause?.message}")
            throw e
        }

    }
}