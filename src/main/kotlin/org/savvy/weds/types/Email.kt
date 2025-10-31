package org.savvy.weds.types

@JvmInline
value class Email(
    val value: String
) {
    init {
        TODO("require(isEmail)")
    }
}