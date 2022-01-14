package com.example.service

import com.example.dto.BookDTO
import com.example.models.Book
import com.example.models.Books
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

//https://github.com/ideaBlender/kotlin-ktor-full-stack/blob/develop/src/jvmMain/kotlin/net/ideablender/kotlinfullstack/repo/TeamRepo.kt
object BooksRepo {

    suspend fun getAllBooks(): List<BookDTO> = dbQuery {
        Book.all().map { it.toDTO() }.toList()
    }

    suspend fun addBooks(books: BookDTO): List<ResultRow>? = transaction {
        Books.insert {
            it[id] = books.id
            it[genre] = books.genre
            it[author] = books.author
            it[title] = books.title
            it[price] = books.price
        }.resultedValues
    }


    suspend fun findBook(id: Int): BookDTO = dbQuery {
        //Books.select { Books.id.eq(id) }.first()
        Book.find { Books.id.eq(id) }.first().toDTO()
    }

}

suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {transaction { block() }   }


