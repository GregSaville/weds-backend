package org.savvy.weds.types

data class GuestToInvite(
    val firstName: String,
    val lastName: String,
    val allowedPartySize: Int
)
