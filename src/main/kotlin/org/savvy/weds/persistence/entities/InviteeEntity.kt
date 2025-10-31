package org.savvy.weds.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.savvy.weds.types.GuestId
import org.savvy.weds.types.RsvpRequestId

@Entity
@Table(name = "invitee")
class InviteeEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: GuestId,

    @Column(name = "first_name", nullable = false, length = 200)
    var firstName: String,

    @Column(name = "last_name", nullable = false, length = 200)
    var lastName: String,

    @Column(name = "allowed_party_size", nullable = false)
    var allowedPartySize: Int,

    @Column(name = "is_notified", nullable = true)
    var isNotified: Boolean = false,

    @Column(name = "rsvp_id", nullable = true)
    var rsvp: RsvpRequestId?
)