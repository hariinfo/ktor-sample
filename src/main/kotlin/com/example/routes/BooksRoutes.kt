package com.example.routes

import com.example.dto.BookDTO
import com.example.models.Customer
import com.example.service.BooksRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

fun Route.books(){
    //val booksService: BooksService
    //val booksService by inject
    route("/books"){
        //Get all books
        get (""){
            call.respond(BooksRepo.getAllBooks())
        }

        //Get a specific book
        get ("{id}"){
            try {
                val id = call.parameters["id"]?: return@get call.respondText ("Missing Id", status = HttpStatusCode.BadRequest)
                val book =
                    BooksRepo.findBook(id.toInt())
                ?: call.respondText("Bad request", status = HttpStatusCode.BadRequest)
                return@get call.respond(book)
            }catch(error: Exception){

            }
        }
        //Insert a book
        post (""){
            val book = call.receive<BookDTO>()
            //booksService.addBooks(book)
            val insertedRecord = BooksRepo.addBooks(book)
            try {
                if (insertedRecord == null){
                    return@post call.respondText("Error creating record!!", status = HttpStatusCode.InternalServerError)
                }
                return@post call.respond(book)
            } catch(err: Exception) {
                return@post call.respondText("Error creating record!!", status = HttpStatusCode.InternalServerError)
            }
            //call.respondText("Book Added", status = HttpStatusCode.OK)
        }


    }
}