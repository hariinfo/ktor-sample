package com.example.routes

import com.example.models.Customer
import com.example.models.customerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.customer() {
    route("/customer") {
        //Get all customers
        get("") {
            if (customerStorage.isNotEmpty()) {
                call.respond(customerStorage)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            }

        }

        //Return a customer
        get("/{id}") {
            val id = call.parameters["id"]?: return@get call.respondText ("Missing Id", status = HttpStatusCode.BadRequest)
            val customer = customerStorage.find { it.id == id } ?: return@get call.respondText("No customer id $id", status = HttpStatusCode.NotFound)
            call.respond(customer)
        }


        //Delete a customer
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("Invalid Request", status = HttpStatusCode.BadRequest)
            if(customerStorage.removeIf { it.id == id }) {
                call.respondText("Customer $id removed successfully!!!", status = HttpStatusCode.Accepted)
            }
            else {
                call.respondText("Customer $id could not be removed!!!", status = HttpStatusCode.NotFound)
            }
        }

        //Add a customer
        post (""){
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
    }


}

