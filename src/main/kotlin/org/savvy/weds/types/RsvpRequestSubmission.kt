package org.savvy.weds.types

data class RsvpRequestSubmission(
    val guestId: GuestId?,
    val fullName: FullName,
    val attendance: RsvpAttendance
)

sealed interface RsvpAttendance {
    data class Declined(val message: String?, val phone: PhoneNumber?, val email: Email?, val address: Address?): RsvpAttendance
    data class Accepted(val specialAccommodations: String?, val phone: PhoneNumber?, val email: Email?, val address: Address?, val additionalGuests: List<AdditionalGuest>?): RsvpAttendance
}

data class AdditionalGuest(
    val firstName: String,
    val lastName: String,
    val specialAccommodations: String?,
)
