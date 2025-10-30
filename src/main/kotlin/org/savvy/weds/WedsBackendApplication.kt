package org.savvy.weds

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WedsBackendApplication

fun main(args: Array<String>) {
	runApplication<WedsBackendApplication>(*args)
}
