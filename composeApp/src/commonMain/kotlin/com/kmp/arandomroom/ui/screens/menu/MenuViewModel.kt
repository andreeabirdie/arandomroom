package com.kmp.arandomroom.ui.screens.menu

import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.action_description_rule
import arandomroom.composeapp.generated.resources.enforce_interactable_objects_rule
import arandomroom.composeapp.generated.resources.generate_game_prompt
import arandomroom.composeapp.generated.resources.object_structure_rule
import arandomroom.composeapp.generated.resources.reply_format_rule
import arandomroom.composeapp.generated.resources.start_end_parameters_rule
import arandomroom.composeapp.generated.resources.empty_rule
import com.kmp.arandomroom.data.model.GameStateDTO
import com.kmp.arandomroom.domain.GenerateContentUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString

class MenuViewModel(
    private val generateContentUseCase: GenerateContentUseCase = GenerateContentUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow<GameStateDTO?>(null)
    val uiState = _uiState.asStateFlow()

    fun generateGame(theme: String) {
        viewModelScope.launch {
            var validResponse = false
            var prompt = getString(Res.string.generate_game_prompt, theme) + " ${
                Json.encodeToString(
                    GameStateDTO.serializer(),
                    GameStateDTO.getDefaultGameState()
                )
            }"

            prompt = addRules(prompt)

            while (!validResponse) {
                println("qwerty $prompt")
                val response = generateContentUseCase.generateContent(prompt)
                val newGameState = response.candidates?.get(0)?.content?.parts?.get(0)?.text
                println("qwerty reply $newGameState")
                if (newGameState != null) {
                    try {
                        _uiState.value = Json.decodeFromString<GameStateDTO>(newGameState)
                        validResponse = true
                    } catch (e: Exception) {
                        println("qwerty error $e")
                    }
                }
            }

        }
    }

    private suspend fun addRules(prompt: String): String {
        return "$prompt \nRules:\n${
            getString(Res.string.start_end_parameters_rule)
        }\n${
            getString(Res.string.enforce_interactable_objects_rule)
        }\n${
            getString(Res.string.action_description_rule)
        }\n${
            getString(Res.string.object_structure_rule)
        }\n${
            getString(Res.string.reply_format_rule)
        }\n${
            getString(Res.string.empty_rule)
        }"
    }

    override fun onCleared() {
        generateContentUseCase.onCleared()
    }
}