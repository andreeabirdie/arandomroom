package com.kmp.arandomroom.ui.features.menu.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrDivider(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(0.75f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "OR",
            modifier = Modifier.padding(horizontal = 8.dp),
        )
        HorizontalDivider(
            modifier = Modifier.weight(0.75f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
