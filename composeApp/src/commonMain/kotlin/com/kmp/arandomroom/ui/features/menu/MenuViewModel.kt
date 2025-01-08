package com.kmp.arandomroom.ui.features.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.describing_future_room
import arandomroom.composeapp.generated.resources.enforce_items_rule
import arandomroom.composeapp.generated.resources.error_message
import arandomroom.composeapp.generated.resources.generate_game_prompt
import arandomroom.composeapp.generated.resources.move_description_rule
import arandomroom.composeapp.generated.resources.unique_ids_rule
import arandomroom.composeapp.generated.resources.validate_items_prompt
import arandomroom.composeapp.generated.resources.validate_moves_prompt
import com.kmp.arandomroom.domain.GameManagementUseCase
import com.kmp.arandomroom.domain.GenerationUseCase
import com.kmp.arandomroom.domain.model.GeneratedGame
import io.github.aakira.napier.Napier
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
            val gameId = Uuid.random().toString()
            var prompt = getString(Res.string.generate_game_prompt, theme)
            prompt = addRules(prompt)

            try {
                val response = generationUseCase.generateResponse(prompt)
                var validatedGame = response?.let { validateMoves(it) }
                validatedGame = validatedGame?.let { validateItems(it) }

                if (validatedGame != null) {
                    val generatedGame =
                        Json.decodeFromString(GeneratedGame.serializer(), validatedGame)
                    Napier.d("Generated Game: $generatedGame")
                    gameManagementUseCase.insertGame(gameId, generatedGame)
                    _uiState.value = _uiState.value.copy(
                        generatedGameId = gameId
                    )
                } else {
                    throw Exception("Failed to generate game")
                }
            } catch (e: Exception) {
                Napier.e("$e ${e.message}")
                gameManagementUseCase.deleteGame(gameId)
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

    private suspend fun validateMoves(generatedGame: String): String? {
        val prompt = getString(
            Res.string.validate_moves_prompt,
            generatedGame
        )

        return generationUseCase.generateResponse(prompt)
    }

    private suspend fun validateItems(generatedGame: String): String? {
        val prompt = getString(
            Res.string.validate_items_prompt,
            generatedGame
        )

        return generationUseCase.generateResponse(prompt)
    }
}