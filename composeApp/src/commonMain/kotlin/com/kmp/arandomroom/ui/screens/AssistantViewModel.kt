package com.kmp.arandomroom.ui.screens

import com.kmp.arandomroom.BuildKonfig
import com.kmp.arandomroom.data.model.Content
import com.kmp.arandomroom.data.model.GenerateContentRequest
import com.kmp.arandomroom.data.model.GenerateContentResponse
import com.kmp.arandomroom.data.model.Part
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


data class AssistantUiState(
    val contentResponse: GenerateContentResponse
)

class AssistantViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AssistantUiState(GenerateContentResponse()))
    val uiState = _uiState.asStateFlow()


    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true;
                ignoreUnknownKeys = true;
                explicitNulls = false}
            )
        }
    }

    fun updateExercises() {
        viewModelScope.launch {
            println("qwerty calling generateContent")
            val response = generateContent("Hi! How are you?")
            _uiState.update { it.copy(contentResponse = response) }
        }
    }

    override fun onCleared() {
        httpClient.close()
    }

    private suspend fun generateContent(prompt: String): GenerateContentResponse {
        val part = Part(text = prompt)
        val contents = Content(listOf(part))
        val request = GenerateContentRequest(contents)

        return httpClient.post("${BuildKonfig.BASE_URL}/gemini-pro:generateContent") {
            contentType(ContentType.Application.Json)
            url { parameters.append("key", BuildKonfig.GEMINI_API_KEY) }
            setBody(request)
        }.body<GenerateContentResponse>()
    }
}