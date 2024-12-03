package com.kmp.arandomroom.ui.screens.room

import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.invalid_action_feedback
import arandomroom.composeapp.generated.resources.validate_action_prompt
import arandomroom.composeapp.generated.resources.using_items_rule
import com.kmp.arandomroom.data.model.Action
import com.kmp.arandomroom.data.model.Action.Companion.getActionType
import com.kmp.arandomroom.data.model.ActionType
import com.kmp.arandomroom.data.model.GameState
import com.kmp.arandomroom.data.model.ValidatedAction
import com.kmp.arandomroom.domain.GenerationUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString

class RoomViewModel(
    private val generationUseCase: GenerationUseCase = GenerationUseCase(ValidatedAction.getSchema())
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameState.getDefaultGameState())
    val uiState = _uiState.asStateFlow()

    fun setInitialGameState(gameState: GameState) {
        _uiState.value = gameState
    }

    fun onAction(action: String) {
        val validActions = _uiState.value.rooms.first { room ->
            room.id == _uiState.value.currentRoom
        }.actions.joinToString(
            ", "
        ) {
            Json.encodeToString(Action.serializer(), it)
        }

        viewModelScope.launch {
            var prompt = getString(
                Res.string.validate_action_prompt,
                validActions,
                action
            )
            prompt = addRules(prompt)
            val response = generationUseCase.generateResponse(prompt)
            if (response != null) {
                val validatedAction = Json.decodeFromString<ValidatedAction>(response)
                performAction(validatedAction)
            }
        }
    }

    private suspend fun addRules(prompt: String): String {
        return "$prompt Rules: ${
            getString(Res.string.using_items_rule)
        }"
    }

    private suspend fun performAction(validatedAction: ValidatedAction) {
        val feedback = validatedAction.actionFeedback.ifEmpty {
            getString(Res.string.invalid_action_feedback)
        }

        _uiState.value = when (validatedAction.action?.getActionType()) {
            ActionType.MOVE -> _uiState.value.copy(
                currentRoom = validatedAction.action.roomId ?: _uiState.value.currentRoom,
                actionFeedback = feedback
            )

            ActionType.PICK_UP -> _uiState.value.copy(
                actionFeedback = feedback,
                inventory = if (validatedAction.action.itemId == null) {
                    _uiState.value.inventory
                } else {
                    _uiState.value.inventory + validatedAction.action.itemId
                }
            )

            else -> _uiState.value.copy(actionFeedback = feedback)
        }
    }
}