package org.savvy.weds.persistence.repos

import jakarta.persistence.EntityManager
import org.savvy.weds.persistence.entities.RsvpRequestEntity
import org.savvy.weds.persistence.entities.StrangerEntity
import org.savvy.weds.types.GuestId
import org.savvy.weds.types.RsvpAttendance
import org.savvy.weds.types.RsvpRequestId
import org.savvy.weds.types.RsvpRequestSubmission
import org.savvy.weds.types.RsvpStatus
import org.springframework.stereotype.Component
import java.util.UUID

interface RsvpRequestRepo {
    fun create(rsvpRequestSubmission: RsvpRequestSubmission): RsvpRequestEntity
    fun findAll(statuses: Set<RsvpStatus>? = null): List<RsvpRequestEntity>
    fun update(rsvpRequestEntity: RsvpRequestEntity): RsvpRequestEntity
    fun existsByGuestId(guestId: GuestId): Boolean
}

@Component
class RsvpRequestRepoImpl(private val em: EntityManager): RsvpRequestRepo {
    override fun create(rsvpRequestSubmission: RsvpRequestSubmission): RsvpRequestEntity {
        val isStrangerId = if (rsvpRequestSubmission.guestId == null) GuestId(UUID.randomUUID()) else null
        val entity = when(val atd = rsvpRequestSubmission.attendance) {
            is RsvpAttendance.Accepted -> {
                RsvpRequestEntity(
                    id = RsvpRequestId(UUID.randomUUID()),
                    inviteeId = rsvpRequestSubmission.guestId,
                    strangerId = isStrangerId,
                    email = atd.email,
                    phone = atd.phone,
                    address = atd.address,
                    status = RsvpStatus.PENDING,
                    additionalGuests = atd.additionalGuests,
                    message = atd.specialAccommodations,
                )
            }
            is RsvpAttendance.Declined -> {
                RsvpRequestEntity(
                    id = RsvpRequestId(UUID.randomUUID()),
                    inviteeId = rsvpRequestSubmission.guestId,
                    strangerId = isStrangerId,
                    email = atd.email,
                    phone = atd.phone,
                    address = atd.address,
                    status = RsvpStatus.DECLINED,
                    additionalGuests = null,
                    message = atd.message,
                )
            }
        }
        
        isStrangerId?.let { strangerId ->

            val fullName = rsvpRequestSubmission.fullName

            val inviteeMatches = em.createQuery(
                "SELECT COUNT(r) FROM RsvpRequestEntity r, InviteeEntity i " +
                        "WHERE r.inviteeId = i.id AND r.status = :pending " +
                        "AND i.firstName = :firstName AND i.lastName = :lastName",
                java.lang.Long::class.java
            )
                .setParameter("pending", RsvpStatus.PENDING)
                .setParameter("firstName", fullName.firstName)
                .setParameter("lastName", fullName.lastName)
                .singleResult

            val strangerMatches = em.createQuery(
                "SELECT COUNT(r) FROM RsvpRequestEntity r, StrangerEntity s " +
                        "WHERE r.strangerId = s.id AND r.status = :pending " +
                        "AND s.firstName = :firstName AND s.lastName = :lastName",
                java.lang.Long::class.java
            )
                .setParameter("pending", RsvpStatus.PENDING)
                .setParameter("firstName", fullName.firstName)
                .setParameter("lastName", fullName.lastName)
                .singleResult

            val totalMatches: Long = inviteeMatches.toLong() + strangerMatches.toLong()
            val hasDuplicate = totalMatches > 0L

            if (hasDuplicate) {
                throw IllegalArgumentException("A similar RSVP submission already exists for the provided full name.")
            }

            em.persist(
                StrangerEntity(
                    id = strangerId,
                    firstName = fullName.firstName,
                    lastName = fullName.lastName,
                    rsvp = entity.id
                )
            )
        }
        em.persist(entity)
        return entity
    }

    override fun findAll(statuses: Set<RsvpStatus>?): List<RsvpRequestEntity> {
        return if (statuses == null || statuses.isEmpty()) {
            em.createQuery("SELECT r FROM RsvpRequestEntity r", RsvpRequestEntity::class.java)
                .resultList
        } else {
            em.createQuery(
                "SELECT r FROM RsvpRequestEntity r WHERE r.status IN :statuses",
                RsvpRequestEntity::class.java
            ).setParameter("statuses", statuses)
                .resultList
        }
    }

    override fun update(rsvpRequestEntity: RsvpRequestEntity): RsvpRequestEntity {
        return em.merge(rsvpRequestEntity)
    }

    override fun existsByGuestId(guestId: GuestId): Boolean {
        val count = em.createQuery(
            "SELECT COUNT(r) FROM RsvpRequestEntity r WHERE r.inviteeId = :guestId OR r.strangerId = :guestId",
            java.lang.Long::class.java
        ).setParameter("guestId", guestId)
            .singleResult
        return count > 0L
    }
}
