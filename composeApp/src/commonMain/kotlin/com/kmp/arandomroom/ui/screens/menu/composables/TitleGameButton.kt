package com.kmp.arandomroom.ui.screens.menu.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.icon_close
import com.kmp.arandomroom.data.model.GameStateDMO
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TitleGameButton(
    game: GameStateDMO,
    isLongPressed: Boolean,
    onLongPress: (String) -> Unit,
    onStartGame: (String) -> Unit,
    onDeleteGame: (String) -> Unit
) {
    Box(modifier = Modifier
        .wrapContentSize()
        .clip(RoundedCornerShape(20.dp))
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .combinedClickable(
            onClick = { if (isLongPressed) onDeleteGame(game.id) else onStartGame(game.id) },
            onLongClick = { onLongPress(game.id) }
        )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = game.title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            if (isLongPressed) {
                Icon(
                    modifier = Modifier.size(20.dp).padding(start = 8.dp),
                    imageVector = vectorResource(Res.drawable.icon_close),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    contentDescription = "Delete game",
                )
            }
        }
    }
}