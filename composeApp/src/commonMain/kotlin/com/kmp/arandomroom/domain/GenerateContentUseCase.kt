package com.kmp.arandomroom.domain

import com.kmp.arandomroom.BuildKonfig
import com.kmp.arandomroom.data.model.prompt.Content
import com.kmp.arandomroom.data.model.prompt.GenerateContentRequest
import com.kmp.arandomroom.data.model.prompt.GenerateContentResponse
import com.kmp.arandomroom.data.model.prompt.Part
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class GenerateContentUseCase {

    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient {
        println("qwerty installing http client")
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }

        install(HttpTimeout) {
            socketTimeoutMillis = 30000
        }
    }

    suspend fun generateContent(prompt: String): GenerateContentResponse {
        println("qwerty called generating content")
        val part = Part(text = prompt)
        val contents = Content(listOf(part))
        val request = GenerateContentRequest(contents)

        return httpClient.post("${BuildKonfig.BASE_URL}/gemini-pro:generateContent") {
            contentType(ContentType.Application.Json)
            url { parameters.append("key", BuildKonfig.GEMINI_API_KEY) }
            setBody(request)
        }.body<GenerateContentResponse>()
    }

    fun onCleared() {
        httpClient.close()
    }
}