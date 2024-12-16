package com.kmp.arandomroom.ui.screens

enum class Routes(val route: String) {
    Menu("Menu"),
    Room("Room/{gameId}"),
    Final("Final");

    fun withArgs(vararg args: String): String {
        return route.replace("{gameId}", args.firstOrNull() ?: "")
    }
}