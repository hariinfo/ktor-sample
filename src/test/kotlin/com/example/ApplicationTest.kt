package com.example

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import io.ktor.util.*
import org.junit.BeforeClass

class ApplicationTest {

    @Test
    fun tesRoot(){
        withServer{
            handleRequest(HttpMethod.Get, "/healthz").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    fun <R> withServer(test: TestApplicationEngine.() -> R) {
        withApplication(createTestEnvironment {
            config = HoconApplicationConfig(ConfigFactory.load("application.conf"))
        }, test = test)
    }
}