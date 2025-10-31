package org.savvy.weds.api

import org.savvy.weds.persistence.repos.InviteeRepo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.UUID

@RestController
@RequestMapping("/admin")
class AdminController(private val inviteeRepo: InviteeRepo) {

    @GetMapping("/health")
    fun health(principal: Principal): Map<String, Any> = mapOf(
        "status" to "ok",
        "scope" to "admin",
        "user" to principal.name
    )

    data class InviteeResponse(
        val id: UUID,
        val firstName: String,
        val lastName: String,
        val allowedPartySize: Int,
        val rsvpId: UUID?
    )

    @GetMapping("/invitees")
    fun getAllInvitees(): ResponseEntity<List<InviteeResponse>> {
        val result = inviteeRepo.findAll()
            .map { i ->
                InviteeResponse(
                    id = i.id.value,
                    firstName = i.firstName,
                    lastName = i.lastName,
                    allowedPartySize = i.allowedPartySize,
                    rsvpId = i.rsvp?.value
                )
            }
        return ResponseEntity.ok(result)
    }
}
