package com.kmp.arandomroom.ui.screens.menu

import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.action_description_rule
import arandomroom.composeapp.generated.resources.enforce_interactable_objects_rule
import arandomroom.composeapp.generated.resources.generate_game_prompt
import arandomroom.composeapp.generated.resources.start_end_parameters_rule
import arandomroom.composeapp.generated.resources.describing_future_room
import com.kmp.arandomroom.data.model.GeneratedGame
import com.kmp.arandomroom.domain.GenerationUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString

class MenuViewModel(
    private val generationUseCase: GenerationUseCase = GenerationUseCase(GeneratedGame.getSchema())
) : ViewModel() {

    private val _uiState = MutableStateFlow<GeneratedGame?>(null)
    val uiState = _uiState.asStateFlow()

    fun generateGame(theme: String) {
        viewModelScope.launch {
            var prompt = getString(Res.string.generate_game_prompt, theme)
            prompt = addRules(prompt)

            val response = generationUseCase.generateResponse(prompt)
            if (response != null) {
                _uiState.value = Json.decodeFromString<GeneratedGame>(response)
            }
        }
    }

    private suspend fun addRules(prompt: String): String {
        return "$prompt Rules: ${
            getString(Res.string.start_end_parameters_rule)
        } ${
            getString(Res.string.enforce_interactable_objects_rule)
        } ${
            getString(Res.string.describing_future_room)
        } ${
            getString(Res.string.action_description_rule)
        }"
    }
}