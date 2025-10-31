package org.savvy.weds.types

data class Address(
    val streetLine1: String,
    val streetLine2: String? = null,
    val neighborhood: String? = null,
    val city: String,
    val municipality: String? = null,
    val state: String,
    val postalCode: String,
    val countryCode: String,
    val formattedAddress: String? = null
)
