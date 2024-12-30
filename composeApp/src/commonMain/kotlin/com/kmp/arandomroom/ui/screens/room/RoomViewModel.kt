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
import com.kmp.arandomroom.domain.model.RoomDTO
import com.kmp.arandomroom.domain.model.ValidatedAction
import com.kmp.arandomroom.domain.GenerationUseCase
import com.kmp.arandomroom.domain.model.ItemDTO
import com.kmp.arandomroom.domain.model.MoveDTO
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

    private val _uiState = MutableStateFlow(RoomState.getDefaultState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val gameState = gameManagementUseCase.getGameState(gameId)
            val inventory = gameManagementUseCase.getGameInventory(gameId)
            val currentRoom = gameManagementUseCase.getRoom(gameId, gameState.currentRoom)
            _uiState.value = _uiState.value.copy(
                gameId = gameId,
                isLoading = false,
                currentRoom = currentRoom,
                endRoom = gameState.endRoom,
                inventory = inventory,
                actionFeedback = ""
            )
        }
    }

    fun onAction(action: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val currentRoom = _uiState.value.currentRoom

        viewModelScope.launch {
            var prompt = getString(
                Res.string.validate_action_prompt,
                currentRoom.description,
                currentRoom.moves.joinToString(", ") {
                    Json.encodeToString(MoveDTO.serializer(), it)
                },
                currentRoom.actions.joinToString(", ") {
                    Json.encodeToString(ActionDTO.serializer(), it)
                },
                _uiState.value.inventory.joinToString(", ") {
                    Json.encodeToString(ItemDTO.serializer(), it)
                },
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

        if (validatedAction.actionId == null) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                actionFeedback = feedback
            )
        }

        validatedAction.actionId?.let { actionId ->
            currentRoom.moves.firstOrNull { it.id == actionId }?.let { moveDTO ->
                performMove(moveDTO, feedback)
            }

            currentRoom.actions.firstOrNull { it.id == actionId }?.let { actionDTO ->
                when (actionDTO.getActionType()) {
                    ActionType.PICK_UP -> {
                        actionDTO.itemId?.let { itemId ->
                            currentRoom.items.find { it.id == itemId }
                        }?.let { itemDTO ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                actionFeedback = feedback,
                                inventory = _uiState.value.inventory.plus(itemDTO)
                            )
                        }
                    }

                    else -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            actionFeedback = feedback
                        )
                    }
                }
            }
        }
    }

    private suspend fun performMove(move: MoveDTO, feedback: String) {
        gameManagementUseCase.setRoomIsVisited(_uiState.value.currentRoom.id)
        val nextRoom = gameManagementUseCase.getRoom(_uiState.value.gameId, move.roomDestinationId)
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            currentRoom = nextRoom,
            actionFeedback = feedback
        )
        gameManagementUseCase.updateGameState(_uiState.value.gameId, nextRoom.id)
    }
}