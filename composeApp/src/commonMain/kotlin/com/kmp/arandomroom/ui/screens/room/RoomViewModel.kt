package com.kmp.arandomroom.ui.screens.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.error_message
import arandomroom.composeapp.generated.resources.invalid_action_feedback
import arandomroom.composeapp.generated.resources.update_description_prompt
import arandomroom.composeapp.generated.resources.validate_action_prompt
import com.kmp.arandomroom.domain.GameManagementUseCase
import com.kmp.arandomroom.domain.model.RoomDTO
import com.kmp.arandomroom.domain.model.ValidatedAction
import com.kmp.arandomroom.domain.GenerationUseCase
import com.kmp.arandomroom.domain.model.ItemDTO
import com.kmp.arandomroom.domain.model.MoveDTO
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent

class RoomViewModel(
    gameId: String,
    private val getActionUseCase: GenerationUseCase,
    private val updateRoomDescriptionUseCase: GenerationUseCase,
    private val gameManagementUseCase: GameManagementUseCase
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(RoomState.getDefaultState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val gameState = gameManagementUseCase.getGameState(gameId)
            val inventory = gameManagementUseCase.getGameInventory(gameId)
            val currentRoom = gameManagementUseCase.getRoom(gameId, gameState.currentRoom)
            gameManagementUseCase.getAllRooms(gameId)
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
            val prompt = getString(
                Res.string.validate_action_prompt,
                action,
                currentRoom.moves.joinSerializedObjects(MoveDTO.serializer()),
                currentRoom.items.joinSerializedObjects(ItemDTO.serializer()),
                currentRoom.description,
                _uiState.value.inventory.joinSerializedObjects(ItemDTO.serializer()),
            )
            Napier.d("prompt: $prompt")
            val errorMessage = getString(Res.string.error_message)
            try {
                val response = getActionUseCase.generateResponse(prompt)
                if (response != null) {
                    val validatedAction = Json.decodeFromString<ValidatedAction>(response)
                    performAction(currentRoom, validatedAction)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    actionFeedback = errorMessage
                )
            }
        }
    }

    fun resetGame() {
        viewModelScope.launch {
            gameManagementUseCase.resetGame(_uiState.value.gameId)
        }
    }

    private suspend fun performAction(
        currentRoom: RoomDTO,
        validatedAction: ValidatedAction
    ) {
        Napier.d("$validatedAction")
        val feedback = validatedAction.actionFeedback.ifEmpty {
            getString(Res.string.invalid_action_feedback)
        }

        if (validatedAction.actionId == null) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                actionFeedback = feedback
            )
            return
        }

        currentRoom.moves.firstOrNull { it.id == validatedAction.actionId }?.let { moveDTO ->
            performMove(moveDTO, feedback)
            return
        }

        currentRoom.items.forEach {
            Napier.d("item: $it")
        }
        currentRoom.items.firstOrNull { it.id == validatedAction.actionId }?.let { itemDTO ->
            performPickUp(itemDTO, feedback)
            return
        }

        _uiState.value = _uiState.value.copy(
            isLoading = false,
            actionFeedback = getString(Res.string.invalid_action_feedback)
        )
    }

    private suspend fun performMove(move: MoveDTO, feedback: String) {
        Napier.d("performing move $move, $feedback")
        var moveFeedback = feedback
        move.requiredItem?.let { item ->
            if (!_uiState.value.inventory.any { it.id == item }) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    actionFeedback = "This way is blocked. You need an item to proceed."
                )
                return
            }
        }
        val nextRoom = gameManagementUseCase.getRoom(
            gameId = _uiState.value.gameId,
            roomId = move.roomDestinationId
        )

        if (nextRoom.id == _uiState.value.endRoom) {
            moveFeedback = moveFeedback.plus(" Congratulations! You have reached the end of the game.")
        }

        _uiState.value = _uiState.value.copy(
            isLoading = false,
            currentRoom = nextRoom,
            actionFeedback = moveFeedback
        )
        gameManagementUseCase.setRoomIsVisited(_uiState.value.currentRoom.id, true)
        gameManagementUseCase.setCurrentRoom(
            gameId = _uiState.value.gameId,
            currentRoomId = nextRoom.id
        )
    }

    private suspend fun performPickUp(item: ItemDTO, feedback: String) {
        Napier.d("picking up $item, $feedback")
        gameManagementUseCase.setRoomIsVisited(_uiState.value.currentRoom.id, true)
        gameManagementUseCase.setItemIsInInventory(item.id, true)
        val inventory = gameManagementUseCase.getGameInventory(_uiState.value.gameId)
        updateRoomDescription(item)
        val currentRoom = gameManagementUseCase.getRoom(
            gameId = _uiState.value.gameId,
            roomId = _uiState.value.currentRoom.id
        )
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            currentRoom = currentRoom,
            inventory = inventory,
            actionFeedback = feedback
        )
    }

    private suspend fun updateRoomDescription(item: ItemDTO) {
        val prompt = getString(
            Res.string.update_description_prompt,
            Json.encodeToString(ItemDTO.serializer(), item),
            _uiState.value.currentRoom.description
        )

        try {
            val response = updateRoomDescriptionUseCase.generateResponse(prompt)
            Napier.d("response: $response")
            if (response != null) {
                gameManagementUseCase.updateRoomDescription(
                    gameId = _uiState.value.gameId,
                    roomId = _uiState.value.currentRoom.id,
                    description = response.trim('"')
                )
            }
        } catch (e: Exception) {
            Napier.e("$e ${e.cause?.message}")

        }
    }
}