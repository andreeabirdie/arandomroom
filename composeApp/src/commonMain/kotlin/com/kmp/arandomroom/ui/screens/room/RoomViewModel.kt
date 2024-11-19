package com.kmp.arandomroom.ui.screens.room

import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.invalid_action_feedback
import arandomroom.composeapp.generated.resources.validate_action_prompt
import arandomroom.composeapp.generated.resources.object_structure_rule
import arandomroom.composeapp.generated.resources.reply_format_rule
import arandomroom.composeapp.generated.resources.empty_rule
import arandomroom.composeapp.generated.resources.using_items_rule
import com.kmp.arandomroom.data.model.Action
import com.kmp.arandomroom.data.model.GameState
import com.kmp.arandomroom.data.model.ValidatedAction
import com.kmp.arandomroom.domain.GenerateContentUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString

class RoomViewModel(
    private val generateContentUseCase: GenerateContentUseCase = GenerateContentUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameState.getDefaultGameState())
    val uiState = _uiState.asStateFlow()

    fun setInitialGameState(gameState: GameState) {
        _uiState.value = gameState
    }

    fun onAction(action: String) {
        viewModelScope.launch {
            var validResponse = false

            val validActions =
                _uiState.value.rooms.first { room ->
                    room.id == _uiState.value.currentRoom
                }.actions.joinToString(
                    ", "
                ) {
                    Json.encodeToString(Action.serializer(), it)
                }

            var prompt = getString(
                Res.string.validate_action_prompt,
                validActions,
                action,
                Json.encodeToString(
                    ValidatedAction.serializer(),
                    ValidatedAction.getDefaultValidatedAction()
                )
            )
            prompt = addRules(prompt)

            while (!validResponse) {
                println("qwerty $prompt")
                val response = generateContentUseCase.generateContent(prompt)
                val validAction = response.candidates?.get(0)?.content?.parts?.get(0)?.text
                println("qwerty reply $validAction")
                if (validAction != null) {
                    try {
                        val validatedAction = Json.decodeFromString<ValidatedAction>(validAction)
                        validResponse = true
                        performAction(validatedAction)
                    } catch (e: Exception) {
                        println("qwerty error e")
                    }
                }
            }
        }
    }

    private suspend fun addRules(prompt: String): String {
        return "$prompt Rules: ${
            getString(Res.string.object_structure_rule)
        }\n${
            getString(Res.string.reply_format_rule)
        }\n${
            getString(Res.string.empty_rule)
        }\n${
            getString(Res.string.using_items_rule)
        }"
    }

    private suspend fun performAction(validatedAction: ValidatedAction) {
        val feedback = validatedAction.actionFeedback.ifEmpty {
            getString(Res.string.invalid_action_feedback)
        }

        _uiState.value = when (validatedAction.action) {
            is Action.Move -> _uiState.value.copy(
                currentRoom = validatedAction.action.roomId,
                actionFeedback = feedback
            )

            is Action.PickUp -> _uiState.value.copy(
                actionFeedback = feedback,
                inventory = _uiState.value.inventory + validatedAction.action.itemId
            )

            else -> _uiState.value.copy(actionFeedback = feedback)
        }
    }

    override fun onCleared() {
        generateContentUseCase.onCleared()
    }
}