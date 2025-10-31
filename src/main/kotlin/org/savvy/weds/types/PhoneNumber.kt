package org.savvy.weds.types

@JvmInline
value class PhoneNumber(
    val value: String
) {
    init {
      TODO("require(isPhoneNumber)")
    }
}