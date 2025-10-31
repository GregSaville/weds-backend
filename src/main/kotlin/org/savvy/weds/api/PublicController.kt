package org.savvy.weds.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public")
class PublicController {

    @GetMapping("/health")
    fun health(): Map<String, Any> = mapOf(
        "status" to "ok",
        "scope" to "public"
    )
}
