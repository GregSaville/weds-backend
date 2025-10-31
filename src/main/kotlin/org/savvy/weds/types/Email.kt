package org.savvy.weds.types

@JvmInline
value class Email(
    val value: String
) {
    init {
        // Basic validation: non-blank, <= 320 chars, simple email regex
        require(value.isNotBlank()) { "Email must not be blank" }
        require(value.length <= 320) { "Email length must be <= 320 characters" }
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        require(emailRegex.matches(value)) { "Invalid email format: $value" }
    }
}