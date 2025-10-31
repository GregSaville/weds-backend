package org.savvy.weds

import org.savvy.weds.input.InputService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WedsBackendApplication

fun main(args: Array<String>) {
    val ctx = runApplication<WedsBackendApplication>(*args)
    ctx.getBean(InputService::class.java).seedGuests()
}
