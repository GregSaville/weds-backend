package org.savvy.weds.rsvp

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "rsvp_request")
class RsvpRequest(
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    var id: UUID? = null,

    @Column(name = "guest_name", nullable = false, length = 200)
    var guestName: String,

    @Column(name = "email", nullable = false, length = 320)
    var email: String,

    @Column(name = "attending", nullable = false)
    var attending: Boolean,

    @Column(name = "guests_count", nullable = false)
    var guestsCount: Int = 1,

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
