package com.book.manager.bookmanager.domain.repositoory

import com.book.manager.bookmanager.domain.model.Book
import com.book.manager.bookmanager.domain.model.BookWithRental
import java.time.LocalDate

interface BookRepoository {
    fun findAllWithRental():List<BookWithRental>
    fun findWithRental(id:Long):BookWithRental?
    fun register(book:Book)
    fun update(id:Long,title:String?,author:String?,releaseDate:LocalDate?)
}