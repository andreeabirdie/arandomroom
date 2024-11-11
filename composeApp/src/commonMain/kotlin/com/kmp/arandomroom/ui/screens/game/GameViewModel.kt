package com.kmp.arandomroom.ui.screens.game

import com.kmp.arandomroom.BuildKonfig
import com.kmp.arandomroom.data.model.Action
import com.kmp.arandomroom.data.model.prompt.Content
import com.kmp.arandomroom.data.model.GameState
import com.kmp.arandomroom.data.model.prompt.GenerateContentRequest
import com.kmp.arandomroom.data.model.prompt.GenerateContentResponse
import com.kmp.arandomroom.data.model.InteractableObject
import com.kmp.arandomroom.data.model.Item
import com.kmp.arandomroom.data.model.prompt.Part
import com.kmp.arandomroom.data.model.Room
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class GameViewModel : ViewModel() {
    val key = Item(
        id = "key1",
        name = "Rusty Key",
        description = "A rusty key, perhaps it opens a door."
    )

    val potion = Item(
        id = "potion1",
        name = "Healing Potion",
        description = "A potion that can restore health."
    )

    val lockedDoor = InteractableObject(
        id = "door1",
        name = "Locked Door",
        description = "A sturdy wooden door. It looks locked.",
        requiredItem = key.id
    )

    val treasureChest = InteractableObject(
        id = "chest1",
        name = "Treasure Chest",
        description = "An old treasure chest. It might contain something valuable."
    )

    // Define rooms
    val room1 = Room(
        id = "room1",
        name = "Starting Room",
        description = "A small, dimly lit room. There's a door to the north.",
        actions = listOf(
            Action.Move(direction = "north", roomId = "room2", feedback = "You move to the next room."),
            Action.PickUp(itemId = key.id, feedback = "You pick up the rusty key.")
        ),
        items = listOf(key),
        interactableObject = listOf(lockedDoor)
    )

    val room2 = Room(
        id = "room2",
        name = "Hallway",
        description = "A long hallway with doors on either side.",
        actions = listOf(
            Action.Move(direction = "south", roomId = "room1", feedback = "You move back to the starting room."),
            Action.Move(direction = "east", roomId = "room3", feedback = "You move to the treasure room."),
            Action.Use(itemId = key.id, objectId = lockedDoor.id, feedback = "You unlock the door.")
        ),
        items = emptyList(),
        interactableObject = listOf(treasureChest)
    )

    val room3 = Room(
        id = "room3",
        name = "Treasure Room",
        description = "A room filled with ancient relics and a treasure chest.",
        actions = listOf(
            Action.Open(objectId = treasureChest.id, feedback = "You open the treasure chest and find a healing potion.")
        ),
        items = listOf(potion),
        interactableObject = listOf(treasureChest)
    )

    val gameState = GameState(
        currentRoom = room1.id,
        endRoom = room3.id,
        rooms = listOf(room1, room2, room3),
        actionFeedback = "",
        inventory = emptyList()
    )

    private val _uiState = MutableStateFlow(gameState)
    val uiState = _uiState.asStateFlow()

    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true;
                ignoreUnknownKeys = true;
                explicitNulls = false}
            )
        }
    }

    fun updateGameState(action: String) {
        viewModelScope.launch {
            val prompt = "Given the following ${Json.encodeToString(GameState.serializer(), gameState)} how would you update the game state given the command: ${action}?. Only actions in the current room are valid. Reply only with new game state json"
            val response = generateContent(prompt)
            val newGameState = response.candidates?.get(0)?.content?.parts?.get(0)?.text
            println(newGameState)
            if (newGameState == null) {
                _uiState.value = _uiState.value.copy(actionFeedback = "You can't do that.")
            } else {
                try {
                    _uiState.value = Json.decodeFromString<GameState>(newGameState)
                } catch (e: Exception) {
                    updateGameState("Couldn't decode previous response. Try again. $prompt")
                }
            }
        }
    }

    private suspend fun generateContent(prompt: String): GenerateContentResponse {
        val part = Part(text = prompt)
        val contents = Content(listOf(part))
        val request = GenerateContentRequest(contents)

        return httpClient.post("${BuildKonfig.BASE_URL}/gemini-pro:generateContent") {
            contentType(ContentType.Application.Json)
            url { parameters.append("key", BuildKonfig.GEMINI_API_KEY) }
            setBody(request)
        }.body<GenerateContentResponse>()
    }

    override fun onCleared() {
        httpClient.close()
    }
}