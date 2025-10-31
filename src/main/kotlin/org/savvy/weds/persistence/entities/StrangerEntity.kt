package org.savvy.weds.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.savvy.weds.types.GuestId
import org.savvy.weds.types.RsvpRequestId

@Entity
@Table(name = "stranger")
class StrangerEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: GuestId,

    @Column(name = "first_name", nullable = false, length = 200)
    var firstName: String,

    @Column(name = "last_name", nullable = false, length = 200)
    var lastName: String,

    @Column(name = "rsvp_id", nullable = false)
    var rsvp: RsvpRequestId
)
