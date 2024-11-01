package com.kmp.arandomroom

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform