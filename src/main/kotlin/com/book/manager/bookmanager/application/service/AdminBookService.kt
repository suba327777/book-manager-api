package com.book.manager.bookmanager.application.service

import com.book.manager.bookmanager.domain.model.Book
import com.book.manager.bookmanager.domain.repositoory.BookRepoository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminBookService(private val bookRepoository: BookRepoository) {
    @Transactional
    fun register(book:Book){
        //登録しようとしているIDデータが既に存在していた場合例外を投げる
        bookRepoository.findWithRental(book.id)?.let { throw java.lang.IllegalArgumentException("既に存在する書籍ID:${book.id}") }
        //なかった場合はbookをそのまま渡している
        bookRepoository.register(book)
    }
}