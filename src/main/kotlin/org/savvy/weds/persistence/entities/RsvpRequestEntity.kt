package org.savvy.weds.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.savvy.weds.types.AdditionalGuest
import org.savvy.weds.types.Address
import org.savvy.weds.types.Email
import org.savvy.weds.types.GuestId
import org.savvy.weds.types.PhoneNumber
import org.savvy.weds.types.RsvpRequestId
import org.savvy.weds.types.RsvpStatus
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "rsvp_request")
class RsvpRequestEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: RsvpRequestId = RsvpRequestId(UUID.randomUUID()),

    @Column(name = "guest_id", nullable = true, length = 200)
    var inviteeId: GuestId?,

    @Column(name = "stranger_id", nullable = true, length = 200)
    var strangerId: GuestId?,

    @Column(name = "email", nullable = true, length = 320)
    var email: Email?,

    @Column(name = "phone", nullable = true, length = 50)
    var phone: PhoneNumber?,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "address", columnDefinition = "JSONB", nullable = true)
    var address: Address?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: RsvpStatus,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_guests", columnDefinition = "JSONB", nullable = true)
    var additionalGuests: List<AdditionalGuest>?,

    @Column(name = "message", columnDefinition = "TEXT")
    var message: String? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = null,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant? = null
) {
    @PrePersist
    fun prePersist() {
        val now = Instant.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }
}