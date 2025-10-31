package org.savvy.weds.types

@JvmInline
value class PhoneNumber(
    val value: String
) {
    init {
        // Basic validation: non-blank, <= 50 chars, 10-15 digits total, allow common formatting and leading +
        require(value.isNotBlank()) { "Phone number must not be blank" }
        require(value.length <= 50) { "Phone number length must be <= 50 characters" }
        val digits = value.filter { it.isDigit() }
        require(digits.length in 10..15) { "Phone number must contain between 10 and 15 digits" }
        val phoneRegex = Regex("^\\+?[0-9() .-]{10,50}$")
        require(phoneRegex.matches(value)) { "Invalid phone number format: $value" }
    }
}