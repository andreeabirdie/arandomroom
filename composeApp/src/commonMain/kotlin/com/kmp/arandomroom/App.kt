package com.kmp.arandomroom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kmp.arandomroom.ui.screens.AssistantViewModel
import com.kmp.arandomroom.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
@Preview
fun App() {
    AppTheme {
        val assistantViewModel = getViewModel(Unit, viewModelFactory { AssistantViewModel() })
        val uiState by assistantViewModel.uiState.collectAsState()
        LaunchedEffect(assistantViewModel) {
            assistantViewModel.updateExercises()
        }

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.safeDrawing),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    uiState.contentResponse.candidates?.get(0)?.content?.parts?.get(0)?.text ?: "No response",
                    Modifier.verticalScroll(rememberScrollState())
                )
            }
        }
    }
}