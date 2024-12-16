package com.kmp.arandomroom.ui.screens.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.invalid_action_feedback
import arandomroom.composeapp.generated.resources.validate_action_prompt
import arandomroom.composeapp.generated.resources.using_items_rule
import com.kmp.arandomroom.domain.model.ActionDTO
import com.kmp.arandomroom.domain.model.ActionDTO.Companion.getActionType
import com.kmp.arandomroom.data.model.ActionType
import com.kmp.arandomroom.domain.GameManagementUseCase
import com.kmp.arandomroom.domain.model.GameStateDTO
import com.kmp.arandomroom.domain.model.RoomDTO
import com.kmp.arandomroom.domain.model.ValidatedAction
import com.kmp.arandomroom.domain.GenerationUseCase
import com.kmp.arandomroom.domain.model.ItemDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent

class RoomViewModel(
    gameId: String,
    private val generationUseCase: GenerationUseCase,
    private val gameManagementUseCase: GameManagementUseCase
) : ViewModel(), KoinComponent {

    private val _uiState =
        MutableStateFlow(
            GameStateDTO(
                gameId = "",
                title = "",
                currentRoom = "",
                endRoom = "",
                rooms = emptyList(),
                actionFeedback = "",
                inventory = emptyList()
            )
        )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = gameManagementUseCase.getGameState(gameId)
        }
    }

    fun onAction(
        currentRoom: RoomDTO,
        action: String
    ) {
        val validActions = currentRoom.actions.joinToString(
            ", "
        ) {
            Json.encodeToString(ActionDTO.serializer(), it)
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
                performAction(currentRoom, validatedAction)
            }
        }
    }

    private suspend fun addRules(prompt: String): String {
        return "$prompt Rules: ${
            getString(Res.string.using_items_rule)
        }"
    }

    private suspend fun performAction(
        currentRoom: RoomDTO,
        validatedAction: ValidatedAction
    ) {
        println("qwerty $validatedAction")
        val feedback = validatedAction.actionFeedback.ifEmpty {
            getString(Res.string.invalid_action_feedback)
        }

        when (validatedAction.action?.getActionType()) {
            ActionType.MOVE -> validatedAction.action.roomDestinationId?.let { roomId ->
                _uiState.value = _uiState.value.copy(
                    currentRoom = roomId,
                    actionFeedback = feedback
                )
            }

            ActionType.PICK_UP -> {
                validatedAction.action.itemId?.let { itemId ->
                    currentRoom.items.find { it.id == itemId }
                }?.let { itemDTO ->
                    _uiState.value = _uiState.value.copy(
                        actionFeedback = feedback,
                        inventory = _uiState.value.inventory.plus(itemDTO)
                    )
                }
            }

            else -> {
                _uiState.value = _uiState.value.copy(actionFeedback = feedback)
            }
        }
    }
}