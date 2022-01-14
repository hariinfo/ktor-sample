package com.example

import com.example.routes.books
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.routes.customer
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import org.jetbrains.exposed.sql.Database


//https://github.com/kiva/guardian-bio-auth/tree/347c59f8c76b2644db853b86b819f8d62d4274ca/bio_auth_service
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    //initialize database
    initDB()


    //API middleware
    install(ContentNegotiation) {
        json()
    }

    install(Routing){
        appHealth()
        customer()
        books()
    }
}

private fun Application.initDB(){
    log.info("---->" + environment.config.property("ktor.connection.url").getString())
    val config = HikariConfig()
    config.driverClassName = environment.config.property("ktor.connection.driverClass").getString()
    config.jdbcUrl = environment.config.property("ktor.connection.url").getString()
    config.username = environment.config.property("ktor.connection.username").getString()
    config.password = environment.config.property("ktor.connection.password").getString()
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)
}

//App health url
fun Route.appHealth() {
    route("/healthz"){
        get {
            call.respondText("OK", status = HttpStatusCode.OK)
        }
    }
}
