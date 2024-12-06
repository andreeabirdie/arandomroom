package com.kmp.arandomroom.ui.screens.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.invalid_action_feedback
import arandomroom.composeapp.generated.resources.validate_action_prompt
import arandomroom.composeapp.generated.resources.using_items_rule
import com.kmp.arandomroom.data.model.Action
import com.kmp.arandomroom.data.model.Action.Companion.getActionType
import com.kmp.arandomroom.data.model.ActionType
import com.kmp.arandomroom.data.model.GameState
import com.kmp.arandomroom.data.model.Room
import com.kmp.arandomroom.data.model.ValidatedAction
import com.kmp.arandomroom.domain.GenerationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent

class RoomViewModel(
    private val generationUseCase: GenerationUseCase,
    private val initialGameState: GameState
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(initialGameState)
    val uiState = _uiState.asStateFlow()

    fun onAction(currentRoom: Room, action: String) {
        val validActions = currentRoom.actions.joinToString(
            ", "
        ) {
            Json.encodeToString(Action.serializer(), it)
        }

        viewModelScope.launch {
            var prompt = getString(
                Res.string.validate_action_prompt,
                currentRoom.description,
                validActions,
                _uiState.value.inventory.joinToString(", "),
                action
            )
            prompt = addRules(prompt)
            val errorMessage = Json.encodeToString(
                serializer = ValidatedAction.serializer(),
                value = ValidatedAction.getErrorMessage()
            )
            val response = generationUseCase.generateResponse(prompt, errorMessage)
            if (response != null) {
                println("qwerty $response")
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
        println("qwerty $validatedAction")
        val feedback = validatedAction.actionFeedback.ifEmpty {
            getString(Res.string.invalid_action_feedback)
        }

         when (validatedAction.action?.getActionType()) {
            ActionType.MOVE -> validatedAction.action.roomId?.let { roomId ->
                _uiState.value = _uiState.value.copy(
                    currentRoom = roomId,
                    actionFeedback = feedback
                )
            }

            ActionType.PICK_UP -> {
                _uiState.value = _uiState.value.copy(
                    actionFeedback = feedback,
                    inventory = if (validatedAction.action.itemId == null) {
                        _uiState.value.inventory
                    } else {
                        _uiState.value.inventory + validatedAction.action.itemId
                    }
                )
            }

            else -> {
                _uiState.value = _uiState.value.copy(actionFeedback = feedback)
            }
        }
    }
}