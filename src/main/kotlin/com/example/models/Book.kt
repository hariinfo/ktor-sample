package com.example.models

import com.example.dto.BookDTO
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object Books: IntIdTable() {

    val genre = varchar("genre", 255)
    val title = varchar(name = "title", 255)
    val author = varchar(name="author", 255)
    val price = double(name="price")
}


class Book(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Book>(Books)
    var genre by Books.genre
    var title by Books.title
    var author by Books.author
    var price by Books.price

    fun toDTO(): BookDTO = BookDTO(id = this.id.value, genre = genre, title = title, author = author, price = price)
}
