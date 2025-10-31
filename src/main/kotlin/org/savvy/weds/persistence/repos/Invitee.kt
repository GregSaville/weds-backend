package org.savvy.weds.persistence.repos

import jakarta.persistence.EntityManager
import org.savvy.weds.persistence.entities.InviteeEntity
import org.savvy.weds.types.GuestId
import org.savvy.weds.types.GuestToInvite
import org.springframework.stereotype.Component
import java.util.UUID

interface InviteeRepo {
    fun create(guestToInvite: GuestToInvite)
    fun findAll(hasRsvp: Boolean? = null): List<InviteeEntity>
    fun update(entity: InviteeEntity): InviteeEntity
}

@Component
class InviteeRepoImpl(private val em: EntityManager): InviteeRepo {
    override fun create(guestToInvite: GuestToInvite) {
        val entity = InviteeEntity(
            id = GuestId(UUID.randomUUID()),
            firstName = guestToInvite.firstName,
            lastName = guestToInvite.lastName,
            allowedPartySize = guestToInvite.allowedPartySize,
            rsvp = null
        )
        em.persist(entity)
    }

    override fun findAll(hasRsvp: Boolean?): List<InviteeEntity> {
        return when (hasRsvp) {
            null -> em.createQuery("SELECT i FROM InviteeEntity i", InviteeEntity::class.java)
                .resultList
            true -> em.createQuery(
                "SELECT i FROM InviteeEntity i WHERE i.rsvp IS NOT NULL",
                InviteeEntity::class.java
            ).resultList
            false -> em.createQuery(
                "SELECT i FROM InviteeEntity i WHERE i.rsvp IS NULL",
                InviteeEntity::class.java
            ).resultList
        }
    }

    override fun update(entity: InviteeEntity): InviteeEntity {
       return em.merge(entity)
    }
}