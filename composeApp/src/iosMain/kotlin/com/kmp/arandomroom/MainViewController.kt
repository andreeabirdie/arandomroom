package com.kmp.arandomroom

import androidx.compose.ui.window.ComposeUIViewController
import com.kmp.arandomroom.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}