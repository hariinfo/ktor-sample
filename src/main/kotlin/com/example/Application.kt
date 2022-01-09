package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.routes.customer
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    log.info("Inside apps 1234")

    install(ContentNegotiation) {
        json()
    }
    install(Routing){
        customer()
    }
}

