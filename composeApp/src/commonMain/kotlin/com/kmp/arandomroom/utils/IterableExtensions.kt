package com.kmp.arandomroom.utils

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

fun <T> Iterable<T>.joinSerializedObjects(
    serializer: SerializationStrategy<T>,
    separator: String = ", "
): String {
    return this.joinToString(separator) {
        Json.encodeToString(serializer, it)
    }
}