package com.book.manager.bookmanager.domain.repositoory

import com.book.manager.bookmanager.domain.model.Book
import com.book.manager.bookmanager.domain.model.BookWithRental

interface BookRepoository {
    fun findAllWithRental():List<BookWithRental>
    fun findWithRental(id:Long):BookWithRental?
    fun register(book:Book)
}