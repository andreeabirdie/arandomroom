package com.kmp.coachme

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform