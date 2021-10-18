package com.example.springwebwebclient

import kotlinx.coroutines.delay
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SpringWebWebclientApplication

fun main(args: Array<String>) {
	runApplication<SpringWebWebclientApplication>(*args)
}

@RestController
class MyHelloController {

	@GetMapping("/hello")
	suspend fun helloThere() {
		trace("Hello there!")
		delay(5_000)
		trace("General Kenobi!")
	}
}

/**
 * Simple console out, but appending the thread name to the message
 */
fun trace(msg: Any) {
	println("[${Thread.currentThread().name}] $msg")
}
