package com.example.core.data

class FilterOutDigits {
    operator fun invoke(text: String): String {
        return text.filter {
            it.isDigit()
        }
    }
}