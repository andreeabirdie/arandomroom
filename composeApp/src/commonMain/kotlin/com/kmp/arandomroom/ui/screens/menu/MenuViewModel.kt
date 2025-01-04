package com.kmp.arandomroom.ui.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.unique_ids_rule
import arandomroom.composeapp.generated.resources.move_description_rule
import arandomroom.composeapp.generated.resources.enforce_items_rule
import arandomroom.composeapp.generated.resources.generate_game_prompt
import arandomroom.composeapp.generated.resources.describing_future_room
import arandomroom.composeapp.generated.resources.error_message
import com.kmp.arandomroom.domain.GameManagementUseCase
import com.kmp.arandomroom.domain.model.GeneratedGame
import com.kmp.arandomroom.domain.GenerationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MenuViewModel(
    private val generationUseCase: GenerationUseCase,
    private val gameManagementUseCase: GameManagementUseCase
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(MenuState.getDefaultState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val games = gameManagementUseCase.getAllGames()
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                games = games
            )
        }
    }

    fun deleteGame(gameId: String) {
        viewModelScope.launch {
            gameManagementUseCase.deleteGame(gameId)
            val games = gameManagementUseCase.getAllGames()
            _uiState.value = _uiState.value.copy(
                games = games,
                error = null
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun generateGame(theme: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )
        viewModelScope.launch {
            var prompt = getString(Res.string.generate_game_prompt, theme)
            prompt = addRules(prompt)

            try {
                val response = generationUseCase.generateResponse(prompt)
                if (response != null) {
                    val generatedGame = Json.decodeFromString(GeneratedGame.serializer(), response)
                    println("qwerty generatedGame: $generatedGame")
                    val gameId = Uuid.random().toString()
                    gameManagementUseCase.insertGame(gameId, generatedGame)
                    _uiState.value = _uiState.value.copy(
                        generatedGameId = gameId
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = getString(Res.string.error_message)
                )
            }
        }
    }

    private suspend fun addRules(prompt: String): String {
        return "$prompt Rules: ${
            getString(Res.string.move_description_rule)
        } ${
            getString(Res.string.describing_future_room)
        } ${
            getString(Res.string.enforce_items_rule)
        } ${
            getString(Res.string.unique_ids_rule)
        }"
    }
}