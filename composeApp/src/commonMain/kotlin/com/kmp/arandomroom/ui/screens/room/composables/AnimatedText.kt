package com.kmp.arandomroom.ui.screens.room.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.kmp.arandomroom.utils.TextCharIterator
import kotlinx.coroutines.delay

@Composable
fun AnimatedText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    val typingDelayInMs = 50L

    var substringText by remember { mutableStateOf("") }
    val charIterator = remember(text) { TextCharIterator(text) }

    LaunchedEffect(charIterator) {
        if (charIterator.isFirst()) {
            substringText = ""
        }
        delay(typingDelayInMs)

        while (charIterator.hasNext()) {
            substringText += charIterator.next()
            delay(typingDelayInMs)
        }
    }

    Text(
        modifier = modifier,
        text = substringText,
        style = style
    )
}
