package com.kmp.arandomroom.utils

class TextCharIterator(val text: String) : Iterator<Char> {
    private var currentIndex = -1

    fun isFirst(): Boolean {
        return currentIndex == -1
    }

    override fun hasNext(): Boolean {
        return currentIndex < text.length - 1
    }

    override fun next(): Char {
        if (currentIndex < text.length) {
            currentIndex++ // Move to the next character
        }
        return text[currentIndex]
    }
}